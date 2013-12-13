package de.pellepelster.myadmin.client.web.modules.dictionary.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.IVOWrapper;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IFilterModel;
import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.filter.DictionaryFilter;
import de.pellepelster.myadmin.client.web.modules.dictionary.result.DictionaryResult;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;
import de.pellepelster.myadmin.client.web.util.DummyAsyncCallback;

public class DictionarySearch<VOType extends IBaseVO> extends BaseDictionaryElement<IDictionaryModel>
{
	private DictionaryResult<VOType> dictionaryResult;

	private List<DictionaryFilter<VOType>> dictionaryFilters = new ArrayList<DictionaryFilter<VOType>>();

	private SearchVOWrapper<VOType> voWrapper = new SearchVOWrapper<VOType>();

	private List<ISearchUpdateListener> updateListeners = new ArrayList<ISearchUpdateListener>();

	public DictionarySearch(IDictionaryModel dictionaryModel)
	{
		super(dictionaryModel, null);

		this.dictionaryResult = new DictionaryResult<VOType>(getModel().getSearchModel().getResultModel(), this);

		for (IFilterModel filterModel : getModel().getSearchModel().getFilterModel())
		{
			this.dictionaryFilters.add(new DictionaryFilter(filterModel, this));
		}
	}

	public void search()
	{
		search(DummyAsyncCallback.dummyAsyncCallback());
	}

	public void search(final AsyncCallback<List<IBaseTable.ITableRow<VOType>>> asyncCallback)
	{
		@SuppressWarnings("unchecked")
		GenericFilterVO<VOType> genericFilter = (GenericFilterVO<VOType>) ClientGenericFilterBuilder.createGenericFilter(getModel().getVOName())
				.getGenericFilter();

		for (Map.Entry<String, Object> filterEntry : this.voWrapper.getFilterValues().entrySet())
		{
			if (filterEntry.getValue() != null && !filterEntry.getValue().toString().isEmpty())
			{
				genericFilter.addCriteria(filterEntry.getKey(), filterEntry.getValue());
			}
		}

		MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(genericFilter, new BaseErrorAsyncCallback<List<VOType>>()
		{

			@Override
			public void onSuccess(List<VOType> result)
			{
				DictionarySearch.this.dictionaryResult.setRows(result);
				asyncCallback.onSuccess(DictionarySearch.this.dictionaryResult.getRows());
				fireUpdateListeners();
			}
		});
	}

	public DictionaryFilter<VOType> getActiveFilter()
	{
		return this.dictionaryFilters.get(0);
	}

	public DictionaryResult<VOType> getDictionaryResult()
	{
		return this.dictionaryResult;
	}

	public boolean hasFilter()
	{
		return !this.dictionaryFilters.isEmpty();
	}

	@Override
	public IVOWrapper<? extends IBaseVO> getVOWrapper()
	{
		return this.voWrapper;
	}

	@Override
	public List<? extends BaseDictionaryElement<?>> getAllChildren()
	{
		List<BaseDictionaryElement<?>> allChildren = new ArrayList<BaseDictionaryElement<?>>();

		for (DictionaryFilter<VOType> dictionaryFilter : this.dictionaryFilters)
		{
			allChildren.addAll(dictionaryFilter.getAllChildren());
		}

		allChildren.addAll(this.dictionaryResult.getAllChildren());

		return allChildren;
	}

	public String getTitle()
	{
		return DictionaryUtil.getSearchLabel(getModel(), getDictionaryResult().getRows().size());
	}

	private void fireUpdateListeners()
	{
		for (ISearchUpdateListener updateListener : this.updateListeners)
		{
			updateListener.onUpdate();
		}
	}

	public void addUpdateListener(ISearchUpdateListener updateListener)
	{
		this.updateListeners.add(updateListener);
	}

}
