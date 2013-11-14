package de.pellepelster.myadmin.client.web.test.modules.dictionary.controls;

import java.util.LinkedList;
import java.util.Map;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.web.test.MyAdminAsyncGwtTestCase.AsyncTestItem;

public class ReferenceControlTestAsyncHelper<VOType extends IBaseVO> extends BaseControlTestAsyncHelper<ReferenceControlTest<VOType>, VOType>
{

	public ReferenceControlTestAsyncHelper(String asyncTestItemResultId, LinkedList<AsyncTestItem> asyncTestItems, Map<String, Object> asyncTestItemResults)
	{
		super(asyncTestItemResultId, asyncTestItems, asyncTestItemResults);
	}

}
