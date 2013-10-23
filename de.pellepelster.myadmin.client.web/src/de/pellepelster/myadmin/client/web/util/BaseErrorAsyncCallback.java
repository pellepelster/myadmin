package de.pellepelster.myadmin.client.web.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseErrorAsyncCallback<T> implements AsyncCallback<T>
{
	private List<AsyncCallback> parentCallbacks = new ArrayList<AsyncCallback>();

	public BaseErrorAsyncCallback()
	{
	}

	public BaseErrorAsyncCallback(AsyncCallback parentCallback)
	{
		this.parentCallbacks.add(parentCallback);
	}

	public void addParentCallback(AsyncCallback parentCallback)
	{
		this.parentCallbacks.add(parentCallback);
	}

	protected void callParentCallbacks(Object result)
	{
		for (AsyncCallback parentCallback : this.parentCallbacks)
		{
			parentCallback.onSuccess(result);
		}
		this.parentCallbacks.clear();
	}

	@Override
	public void onFailure(Throwable caught)
	{
		if (this.parentCallbacks.isEmpty())
		{
			throw new RuntimeException(caught);
		}

		for (AsyncCallback parentCallback : this.parentCallbacks)
		{
			parentCallback.onFailure(caught);
		}
		this.parentCallbacks.clear();
	}
}
