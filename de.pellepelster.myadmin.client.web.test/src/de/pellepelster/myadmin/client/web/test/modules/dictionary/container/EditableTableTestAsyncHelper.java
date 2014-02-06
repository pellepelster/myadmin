package de.pellepelster.myadmin.client.web.test.modules.dictionary.container;

import java.util.LinkedList;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.web.test.MyAdminAsyncGwtTestCase.AsyncTestItem;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.BaseAsyncHelper;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

public class EditableTableTestAsyncHelper<VOTYpe extends IBaseVO> extends BaseAsyncHelper<EditableTableTest<VOTYpe>>
{

	public EditableTableTestAsyncHelper(String asyncTestItemResultId, LinkedList<AsyncTestItem> asyncTestItems, Map<String, Object> asyncTestItemResults)
	{
		super(asyncTestItemResultId, asyncTestItems, asyncTestItemResults);
	}

	public void add()
	{
		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(final AsyncCallback<Object> asyncCallback)
			{
				getAsyncTestItemResult().add(new BaseErrorAsyncCallback()
				{
					@Override
					public void onSuccess(Object result)
					{
						asyncCallback.onSuccess(getAsyncTestItemResult());
					}
				});

			}

			@Override
			public String getDescription()
			{
				return "add";
			}
		});
	}

	public void delete()
	{
		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(final AsyncCallback<Object> asyncCallback)
			{
				getAsyncTestItemResult().delete(new BaseErrorAsyncCallback()
				{
					@Override
					public void onSuccess(Object result)
					{
						asyncCallback.onSuccess(getAsyncTestItemResult());
					}
				});

			}

			@Override
			public String getDescription()
			{
				return "delete";
			}
		});
	}

	public void assertRowCount(final int expectedRowCount)
	{
		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				getAsyncTestItemResult().assertRowCount(expectedRowCount);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription()
			{
				return "assertRowCount(" + expectedRowCount + ")";
			}

		});
	}

}