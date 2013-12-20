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

import de.pellepelster.myadmin.client.web.test.MyAdminAsyncGwtTestCase;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionaryEditorModuleTestUIAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionarySearchModuleTestUIAsyncHelper;
import de.pellepelster.myadmin.demo.client.web.dictionaries.DemoDictionaryModel;
import de.pellepelster.myadmin.demo.client.web.entities.CityVO;

public class DemoClientCityTest extends MyAdminAsyncGwtTestCase<CityVO>
{

	@Override
	public String getModuleName()
	{
		return "de.pellepelster.myadmin.demo.DemoTest";
	}

	@Test
	public void testCreateAndSearch()
	{
		deleteAllVOs(CityVO.class);

		DictionarySearchModuleTestUIAsyncHelper<CityVO> search = openSearch(DemoDictionaryModel.CITY);
		search.assertTitle("CitySearch (0 results)");
		search.assertResultCount(0);

		DictionaryEditorModuleTestUIAsyncHelper<CityVO> editor = openEditor(DemoDictionaryModel.CITY);
		editor.assertTitle("CityEditor (New)");

		editor.getTextControlTest(DemoDictionaryModel.CITY.CITY_EDITOR.COMPOSITE2.COMPOSITE3.CITY_NAME).setValue("xxx");
		editor.assertTitle("CityEditor (New) *");
		editor.save();

		search.execute();
		search.assertTitle("CitySearch (1 result)");
		search.assertResultCount(1);

		search.getTextControlTest(DemoDictionaryModel.CITY.CITY_SEARCH.CITY_FILTER.COMPOSITE1.CITY_NAME).setValue("yyy");
		search.execute();
		search.assertTitle("CitySearch (0 results)");
		search.assertResultCount(0);

		search.getTextControlTest(DemoDictionaryModel.CITY.CITY_SEARCH.CITY_FILTER.COMPOSITE1.CITY_NAME).setValue("");
		search.execute();
		search.assertResultCount(1);

		runAsyncTests();
	}
}
