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
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.BaseEditableTableModel;
import de.pellepelster.myadmin.client.web.test.MyAdminAsyncGwtTestCase.AsyncTestItem;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.container.EditableTableTestAsyncHelper;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

@SuppressWarnings("rawtypes")
public class DictionaryEditorModuleTestUIAsyncHelper<VOType extends IBaseVO> extends
		BaseDictionaryModuleTestUIAsyncHelper<DictionaryEditorModuleTestUI, VOType>
{
	public DictionaryEditorModuleTestUIAsyncHelper(String asynTestItemResultId, LinkedList<AsyncTestItem> asyncTestItems,
			Map<String, Object> asyncTestItemResults)
	{
		super(asynTestItemResultId, asyncTestItems, asyncTestItemResults);
	}

	public void assertTitle(final String expectedTitle)
	{
		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				getAsyncTestItemResult().assertTitle(expectedTitle);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});
	}

	public void assertHasNoErrors()
	{
		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				getAsyncTestItemResult().assertHasNoErrors();
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});
	}

	public void assertHasErrors(final int errorCount)
	{
		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				getAsyncTestItemResult().assertHasErrors(errorCount);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});
	}

	public <TableVOType extends IBaseVO> EditableTableTestAsyncHelper<TableVOType> getEditableTableTest(
			final BaseEditableTableModel<TableVOType> tableModel)
	{
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				DictionaryEditorModuleTestUIAsyncHelper.this.getAsyncTestItemResults()
						.put(uuid, getAsyncTestItemResult().getEditableTableTest(tableModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});

		return new EditableTableTestAsyncHelper<TableVOType>(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public void save()
	{
		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(final AsyncCallback<Object> asyncCallback)
			{
				getAsyncTestItemResult().save(new BaseErrorAsyncCallback<DictionaryEditorModuleTestUI>()
				{

					@Override
					public void onSuccess(DictionaryEditorModuleTestUI result)
					{
						asyncCallback.onSuccess(getAsyncTestItemResult());
					}
				});
			}
		});
	}
}
