package de.pellepelster.myadmin.client.web.modules.dictionary.result;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IResultModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.search.IDictionaryResult;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.BaseTable;

public class DictionaryResult<VOType extends IBaseVO> extends BaseTable<VOType, IResultModel> implements IDictionaryResult
{
	public final static String CONTROL_FIRST_EDIT_DATA_KEY = "CONTROL_FIRST_EDIT_DATA_KEY";

	public DictionaryResult(IResultModel resultModel, BaseModelElement parent)
	{
		super(resultModel, parent);
	}

}
