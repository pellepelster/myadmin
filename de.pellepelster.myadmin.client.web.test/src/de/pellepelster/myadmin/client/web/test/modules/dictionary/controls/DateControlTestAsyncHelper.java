package de.pellepelster.myadmin.client.web.test.modules.dictionary.controls;

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.web.test.MyAdminAsyncGwtTestCase.AsyncTestItem;

public class DateControlTestAsyncHelper extends BaseControlTestAsyncHelper<DateControlTest, Date>
{

	public DateControlTestAsyncHelper(String asyncTestItemResultId, LinkedList<AsyncTestItem> asyncTestItems, Map<String, Object> asyncTestItemResults)
	{
		super(asyncTestItemResultId, asyncTestItems, asyncTestItemResults);
	}

	public void assertValueWithoutMillies(final Date expectedValue)
	{
		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				getAsyncTestItemResult().assertValueWithoutMillies(expectedValue);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription()
			{
				return "assertValueWithoutMillies(" + expectedValue + ")";
			}
		});
	}

}
