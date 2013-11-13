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

import junit.framework.Assert;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.web.test.MyAdminAsyncGwtTestCase.AsyncTestItem;

@SuppressWarnings("rawtypes")
public class DictionaryEditorModuleTestUIAsyncHelper<VOType extends IBaseVO>
{
	private LinkedList<AsyncTestItem> asyncTestItems;

	public DictionaryEditorModuleTestUIAsyncHelper(LinkedList<AsyncTestItem> asyncTestItems)
	{
		super();
		this.asyncTestItems = asyncTestItems;
	}

	// public TextControlTest
	// getTextControlTest(DictionaryDescriptor<ITextControl> controlDescriptor)
	// {
	// this.asyncTestItems.add(new AsyncTestItem<DictionaryEditorModuleTestUI>()
	// {
	// @Override
	// public void run(AsyncCallback<Object> asyncCallback,
	// DictionaryEditorModuleTestUI lastResult)
	// {
	// Assert.assertEquals(expectedTitle, lastResult.getTitle());
	// asyncCallback.onSuccess(DictionaryEditorModuleTestUIAsyncHelper.this);
	// }
	// });
	//
	// return new TextControlTest(this.module.getElement(controlDescriptor));
	// }

	public void assertTitle(final String expectedTitle)
	{
		this.asyncTestItems.add(new AsyncTestItem<DictionaryEditorModuleTestUI>()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback, DictionaryEditorModuleTestUI lastResult)
			{
				Assert.assertEquals(expectedTitle, lastResult.getTitle());
				asyncCallback.onSuccess(DictionaryEditorModuleTestUIAsyncHelper.this);
			}
		});
	}
}
