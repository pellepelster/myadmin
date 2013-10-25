package de.pellepelster.myadmin.client.web.modules.dictionary.search;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.ISearchModel;
import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.result.DictionaryResult;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

public class DictionarySearch<VOType extends IBaseVO> extends BaseModelElement<ISearchModel>
{
	private DictionaryResult<VOType> dictionaryResult;

	public DictionarySearch(ISearchModel searchModel)
	{
		super(searchModel, null);

		this.dictionaryResult = new DictionaryResult<VOType>(searchModel.getResultModel(), this);
	}

	public void search(final AsyncCallback<List<IBaseTable.ITableRow<VOType>>> asyncCallback)
	{
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
}
