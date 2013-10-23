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
package de.pellepelster.myadmin.demo.client.test;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IEditableTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.ITextControl;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;
import de.pellepelster.myadmin.client.web.test.MyAdminTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionaryEditorModuleTestUI;
import de.pellepelster.myadmin.client.web.test.modules.navigation.NavigationModuleTestUI;
import de.pellepelster.myadmin.client.web.test.modules.navigation.NavigationTreeTestElements;
import de.pellepelster.myadmin.client.web.test.vo.Test3VO;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;
import de.pellepelster.myadmin.demo.client.web.DemoDictionaryIDs;

public class DemoClientTest extends GWTTestCase
{

	@Override
	public String getModuleName()
	{
		return "de.pellepelster.myadmin.demo.DemoTest";
	}

	private class NavigationTreeElementTest extends BaseErrorAsyncCallback<NavigationTreeTestElements>
	{
		@Override
		public void onSuccess(NavigationTreeTestElements result)
		{
			result.assertChildrenCount(2);
			result.assertChildNavigationText(0, "Masterdata");
			result.assertChildNavigationText(1, "Test");
			finishTest();
		}
	}

	private class NavigationModuleTest extends BaseErrorAsyncCallback<NavigationModuleTestUI>
	{
		@Override
		public void onSuccess(NavigationModuleTestUI result)
		{
			result.getRootElements(new NavigationTreeElementTest());
		}
	}

	private class TestCityEditorSaveResult extends BaseErrorAsyncCallback<DictionaryEditorModuleTestUI<IBaseVO>>
	{
		@Override
		public void onSuccess(DictionaryEditorModuleTestUI<IBaseVO> result)
		{
			ITextControl textControl1 = result.getElement(DemoDictionaryIDs.CITY.CITY_EDITOR.COMPOSITE2.COMPOSITE3.CITY_NAME);
			assertEquals("Hamburg", textControl1.getValue());

			finishTest();
		}
	}

	private class TestDictionary1EditorSaveResult extends BaseErrorAsyncCallback<DictionaryEditorModuleTestUI<IBaseVO>>
	{
		@Override
		public void onSuccess(DictionaryEditorModuleTestUI<IBaseVO> result)
		{
			ITextControl textControl1 = result.getElement(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1);
			assertEquals("text1", textControl1.getValue());

			IEditableTable<Test3VO> editableTable = result.getElement(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_EDITABLE_TABLE1);
			editableTable.add(new BaseErrorAsyncCallback<IBaseTable.ITableRow<Test3VO>>()
			{
				@Override
				public void onSuccess(IBaseTable.ITableRow<Test3VO> result)
				{
					finishTest();
				}
			});

		}
	}

	private class TestCityEditorSave extends BaseErrorAsyncCallback<DictionaryEditorModuleTestUI<IBaseVO>>
	{
		@Override
		public void onSuccess(DictionaryEditorModuleTestUI<IBaseVO> result)
		{
			ITextControl textControl1 = result.getElement(DemoDictionaryIDs.CITY.CITY_EDITOR.COMPOSITE2.COMPOSITE3.CITY_NAME);
			textControl1.setValue("Hamburg");

			result.save(new TestCityEditorSaveResult());
		}
	}

	private class TestDictionary1EditorSave extends BaseErrorAsyncCallback<DictionaryEditorModuleTestUI<IBaseVO>>
	{
		@Override
		public void onSuccess(DictionaryEditorModuleTestUI<IBaseVO> result)
		{
			ITextControl textControl1 = result.getElement(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1);
			textControl1.setValue("text1");

			result.save(new TestDictionary1EditorSaveResult());
		}
	}

	@Test
	public void testNavigationTree()
	{
		MyAdminTest.getInstance().startModule(ModuleNavigationModule.MODULE_ID, NavigationModuleTestUI.class, Direction.WEST.toString(),
				new NavigationModuleTest());

		delayTestFinish(2000);
	}

	@Test
	public void testCity()
	{
		MyAdminTest.getInstance().openEditor(DemoDictionaryIDs.CITY, new TestCityEditorSave());

		delayTestFinish(2000);
	}

	@Test
	public void testDictionary1()
	{
		MyAdminTest.getInstance().openEditor(DemoDictionaryIDs.DICTIONARY1, new TestDictionary1EditorSave());

		delayTestFinish(2000);
	}

}
