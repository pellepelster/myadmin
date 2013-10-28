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

import de.pellepelster.myadmin.client.web.test.MyAdminTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionaryEditorModuleTestUI;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionarySearchModuleTestUI;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;
import de.pellepelster.myadmin.demo.client.web.DemoDictionaryIDs;
import de.pellepelster.myadmin.demo.client.web.entities.CityVO;

public class DemoClientCityTest extends GWTTestCase
{

	@Override
	public String getModuleName()
	{
		return "de.pellepelster.myadmin.demo.DemoTest";
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

	private class TestCityEditorSave extends BaseErrorAsyncCallback<DictionaryEditorModuleTestUI<CityVO>>
	{
		@Override
		public void onSuccess(DictionaryEditorModuleTestUI<CityVO> result)
		{
			result.getBaseControlTestElement(DemoDictionaryIDs.CITY.CITY_EDITOR.COMPOSITE2.COMPOSITE3.CITY_NAME).setValue("Hamburg");
			result.save(new TestCityEditorSaveResult());
		}
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

}
