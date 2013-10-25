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

import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;
import de.pellepelster.myadmin.client.web.test.MyAdminTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionaryEditorModuleTestUI;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionarySearchModuleTestUI;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.EditableTableTest;
import de.pellepelster.myadmin.client.web.test.modules.navigation.NavigationModuleTestUI;
import de.pellepelster.myadmin.client.web.test.modules.navigation.NavigationTreeTestElements;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;
import de.pellepelster.myadmin.demo.client.web.DemoDictionaryIDs;
import de.pellepelster.myadmin.demo.client.web.entities.CityVO;
import de.pellepelster.myadmin.demo.client.web.test1.Test1VO;
import de.pellepelster.myadmin.demo.client.web.test1.Test3VO;

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

	private class TestCitySearchResult extends BaseErrorAsyncCallback<DictionarySearchModuleTestUI<CityVO>>
	{

		@Override
		public void onSuccess(DictionarySearchModuleTestUI<CityVO> result)
		{
			result.assertResultCount(1);
			result.assertTitle("CitySearch (1 result)");
			result.getResultTableRow(0).getBaseControlTestElement(DemoDictionaryIDs.CITY.CITY_SEARCH.CITY_RESULT.CITY_NAME).assertValue("Hamburg");

			finishTest();
		}
	}

	private class TestCitySearch extends BaseErrorAsyncCallback<DictionarySearchModuleTestUI<CityVO>>
	{

		@Override
		public void onSuccess(DictionarySearchModuleTestUI<CityVO> result)
		{
			result.assertTitle("CitySearch (0 results)");
			result.search(new TestCitySearchResult());
		}
	}

	private class TestCityEditorSaveResult extends BaseErrorAsyncCallback<DictionaryEditorModuleTestUI<CityVO>>
	{
		@Override
		public void onSuccess(DictionaryEditorModuleTestUI<CityVO> result)
		{
			result.getBaseControlTestElement(DemoDictionaryIDs.CITY.CITY_EDITOR.COMPOSITE2.COMPOSITE3.CITY_NAME).assertValue("Hamburg");
			MyAdminTest.getInstance().openSearch(DemoDictionaryIDs.CITY, new TestCitySearch());
		}
	}

	private class TestDictionary1EditorSaveResult extends BaseErrorAsyncCallback<DictionaryEditorModuleTestUI<Test1VO>>
	{
		@Override
		public void onSuccess(DictionaryEditorModuleTestUI<Test1VO> result)
		{
			result.getBaseControlTestElement(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1).assertValue("text1");

			final EditableTableTest<Test3VO> editableTable = result
					.getEditableTableTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_EDITABLE_TABLE1);

			editableTable.add(new BaseErrorAsyncCallback<EditableTableTest<Test3VO>>()
			{
				@Override
				public void onSuccess(EditableTableTest<Test3VO> result)
				{
					editableTable.assertRowCount(1);

					finishTest();
				}
			});

		}
	}

	private class TestCityEditorSave extends BaseErrorAsyncCallback<DictionaryEditorModuleTestUI<CityVO>>
	{
		@Override
		public void onSuccess(DictionaryEditorModuleTestUI<CityVO> result)
		{
			result.getBaseControlTestElement(DemoDictionaryIDs.CITY.CITY_EDITOR.COMPOSITE2.COMPOSITE3.CITY_NAME).setValue("Hamburg");
			result.save(new TestCityEditorSaveResult());
		}
	}

	private class TestDictionary1EditorSave extends BaseErrorAsyncCallback<DictionaryEditorModuleTestUI<Test1VO>>
	{
		@Override
		public void onSuccess(DictionaryEditorModuleTestUI<Test1VO> result)
		{
			result.getBaseControlTestElement(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1).setValue("text1");
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
		MyAdminTest.getInstance().deleteAllVOs(CityVO.class, new BaseErrorAsyncCallback()
		{
			@Override
			public void onSuccess(Object result)
			{
				MyAdminTest.getInstance().openEditor(DemoDictionaryIDs.CITY, new TestCityEditorSave());
			}
		});

		delayTestFinish(2000);
	}

	@Test
	public void testDictionary1()
	{
		MyAdminTest.getInstance().openEditor(DemoDictionaryIDs.DICTIONARY1, new TestDictionary1EditorSave());

		delayTestFinish(2000);
	}

}
