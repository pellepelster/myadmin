package de.pellepelster.myadmin.client.web.util;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseErrorAsyncCallback<T> implements AsyncCallback<T>
{
	private AsyncCallback<?> parentCallback = null;

	public BaseErrorAsyncCallback()
	{
	}

	public BaseErrorAsyncCallback(AsyncCallback<?> parentCallback)
	{
		this.parentCallback = parentCallback;
	}

	@Override
	public void onFailure(Throwable caught)
	{
		if (parentCallback == null )
		{
			throw new RuntimeException(caught);
		}
		else
		{
			parentCallback.onFailure(caught);
		}
	}
}
