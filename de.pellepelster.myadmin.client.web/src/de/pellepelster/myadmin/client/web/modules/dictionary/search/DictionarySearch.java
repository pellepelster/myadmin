package de.pellepelster.myadmin.client.web.modules.dictionary.search;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IFilterModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.ISearchModel;
import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.filter.DictionaryFilter;
import de.pellepelster.myadmin.client.web.modules.dictionary.result.DictionaryResult;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;
import de.pellepelster.myadmin.client.web.util.DummyAsyncCallback;

public class DictionarySearch<VOType extends IBaseVO> extends BaseDictionaryElement<ISearchModel>
{
	private DictionaryResult<VOType> dictionaryResult;

	private List<DictionaryFilter<VOType>> dictionaryFilters = new ArrayList<DictionaryFilter<VOType>>();

	public DictionarySearch(ISearchModel searchModel)
	{
		super(searchModel, null);

		this.dictionaryResult = new DictionaryResult<VOType>(searchModel.getResultModel(), this);

		for (IFilterModel filterModel : searchModel.getFilterModel())
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

		MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(genericFilter, new BaseErrorAsyncCallback<List<VOType>>()
		{

			@Override
			public void onSuccess(List<VOType> result)
			{
				DictionarySearch.this.dictionaryResult.setRows(result);
				asyncCallback.onSuccess(DictionarySearch.this.dictionaryResult.getRows());
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

}
