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

import java.util.List;

import junit.framework.Assert;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.ITextControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearch;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModule;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.TextControlTest;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class DictionarySearchModuleTestUI<VOType extends IBaseVO> implements IModuleUI<Object, DictionarySearchModule<VOType>>
{
	private DictionarySearchModule<VOType> module;

	public DictionarySearchModuleTestUI(DictionarySearchModule<VOType> module)
	{
		super();
		this.module = module;
	}

	@Override
	public boolean close()
	{
		return false;
	}

	@Override
	public boolean contributesToBreadCrumbs()
	{
		return false;
	}

	@Override
	public Object getContainer()
	{
		return null;
	}

	@Override
	public DictionarySearchModule<VOType> getModule()
	{
		return this.module;
	}

	@Override
	public String getTitle()
	{
		return this.module.getTitle();
	}

	public DictionarySearch<VOType> getDictionarySearch()
	{
		return this.module.getDictionarySearch();
	}

	public TextControlTest getTextControlTest(DictionaryDescriptor<ITextControl> controlDescriptor)
	{
		return new TextControlTest(this.module.getElement(controlDescriptor));
	}

	public void search(final AsyncCallback<DictionarySearchModuleTestUI<VOType>> asyncCallback)
	{
		this.module.getDictionarySearch().search(new BaseErrorAsyncCallback<List<IBaseTable.ITableRow<VOType>>>()
		{
			@Override
			public void onSuccess(List<ITableRow<VOType>> result)
			{
				asyncCallback.onSuccess(DictionarySearchModuleTestUI.this);
			}
		});

	}

	public TableRowTest<VOType> getResultTableRow(int rowIndex)
	{
		return new TableRowTest<VOType>(this.module.getDictionarySearch().getDictionaryResult().getTableRow(rowIndex));
	}

	public void assertTitle(String expectedTitle)
	{
		Assert.assertEquals(expectedTitle, this.module.getTitle());
	}

	public void assertResultCount(int expectedResultCount)
	{
		Assert.assertEquals(expectedResultCount, this.module.getDictionarySearch().getDictionaryResult().getRows().size());
	}

}
