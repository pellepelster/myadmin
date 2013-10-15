package de.pellepelster.myadmin.client.web.util;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseAsyncCallback<T> implements AsyncCallback<T>
{
	private AsyncCallback parentCallback;

	public BaseAsyncCallback()
	{
	}

	public BaseAsyncCallback(AsyncCallback parentCallback)
	{
		this.parentCallback = parentCallback;
	}

	public AsyncCallback getParentCallback()
	{
		return this.parentCallback;
	}

	@Override
	public void onFailure(Throwable caught)
	{
		if (this.parentCallback != null)
		{
			this.parentCallback.onFailure(caught);
		}
		else
		{
			throw new RuntimeException(caught);

		}
	}

}
