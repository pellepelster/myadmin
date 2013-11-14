/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package de.pellepelster.myadmin.client.web.test.modules.dictionary;

import java.util.LinkedList;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.UUID;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.ITextControl;
import de.pellepelster.myadmin.client.web.test.MyAdminAsyncGwtTestCase.AsyncTestItem;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.TextControlTestAsyncHelper;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

@SuppressWarnings("rawtypes")
public class DictionarySearchModuleTestUIAsyncHelper<VOType extends IBaseVO> extends BaseAsyncHelper<DictionarySearchModuleTestUI>
{
	public DictionarySearchModuleTestUIAsyncHelper(String parentUuid, LinkedList<AsyncTestItem> asyncTestItems, Map<String, Object> asyncTestItemResults)
	{
		super(parentUuid, asyncTestItems, asyncTestItemResults);
	}

	public TextControlTestAsyncHelper getTextControlTest(final DictionaryDescriptor<ITextControl> controlDescriptor)
	{
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				DictionarySearchModuleTestUIAsyncHelper.this.getAsyncTestItemResults()
						.put(uuid, getAsyncTestItemResult().getTextControlTest(controlDescriptor));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});

		return new TextControlTestAsyncHelper(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public void assertTitle(final String expectedTitle)
	{
		this.getAsyncTestItems().add(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				getAsyncTestItemResult().assertTitle(expectedTitle);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});
	}

	public void assertResultCount(final int expectedResultCount)
	{
		this.getAsyncTestItems().add(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				getAsyncTestItemResult().assertResultCount(expectedResultCount);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});
	}

	public void execute()
	{
		this.getAsyncTestItems().add(new AsyncTestItem()
		{
			@Override
			public void run(final AsyncCallback<Object> asyncCallback)
			{
				getAsyncTestItemResult().search(new BaseErrorAsyncCallback<DictionarySearchModuleTestUI>()
				{
					@Override
					public void onSuccess(DictionarySearchModuleTestUI result)
					{
						asyncCallback.onSuccess(getAsyncTestItemResult());
					}
				});
			}
		});
	}
}
