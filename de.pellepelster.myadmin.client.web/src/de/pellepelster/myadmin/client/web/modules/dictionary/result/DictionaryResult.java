package de.pellepelster.myadmin.client.web.modules.dictionary.result;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IResultModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.search.IDictionaryResult;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.BaseTableElement;

public class DictionaryResult<VOType extends IBaseVO> extends BaseTableElement<VOType, IResultModel> implements IDictionaryResult
{
	public final static String CONTROL_FIRST_EDIT_DATA_KEY = "CONTROL_FIRST_EDIT_DATA_KEY";

	public DictionaryResult(IResultModel resultModel, BaseDictionaryElement parent)
	{
		super(resultModel, parent);
	}

}
