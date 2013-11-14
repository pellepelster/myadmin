package de.pellepelster.myadmin.client.web.test.modules.dictionary.controls;

import java.util.LinkedList;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.web.test.MyAdminAsyncGwtTestCase.AsyncTestItem;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.BaseAsyncHelper;

public abstract class BaseControlTestAsyncHelper<T extends BaseControlTest> extends BaseAsyncHelper<T>
{

	public BaseControlTestAsyncHelper(String asyncTestItemResultId, LinkedList<AsyncTestItem> asyncTestItems, Map<String, Object> asyncTestItemResults)
	{
		super(asyncTestItemResultId, asyncTestItems, asyncTestItemResults);
	}

	public void setValue(final String value)
	{
		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				getAsyncTestItemResult().setValue(value);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});
	}

	public void assertValue(final String expectedValue)
	{
		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				getAsyncTestItemResult().assertValue(expectedValue);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});
	}
}
