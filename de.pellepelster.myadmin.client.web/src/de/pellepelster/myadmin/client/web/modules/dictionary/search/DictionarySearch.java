package de.pellepelster.myadmin.client.web.modules.dictionary.search;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.ISearchModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.result.DictionaryResult;

public class DictionarySearch<VOType extends IBaseVO> extends BaseModelElement<ISearchModel>
{
	private DictionaryResult<VOType> dictionaryResult;

	public DictionarySearch(ISearchModel searchModel)
	{
		super(searchModel, null);

		this.dictionaryResult = new DictionaryResult<VOType>(searchModel.getResultModel(), this);
	}

	public void search(AsyncCallback<List<IBaseTable.ITableRow<VOType>>> asyncCallback)
	{

	}

}
