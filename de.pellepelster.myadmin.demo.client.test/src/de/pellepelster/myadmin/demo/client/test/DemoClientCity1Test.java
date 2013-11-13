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
import de.pellepelster.myadmin.demo.client.web.DemoDictionaryIDs;
import de.pellepelster.myadmin.demo.client.web.entities.CityVO;

public class DemoClientCity1Test extends MyAdminAsyncGwtTestCase<CityVO>
{

	@Override
	public String getModuleName()
	{
		return "de.pellepelster.myadmin.demo.DemoTest";
	}

	@Test
	public void testCity1()
	{
		deleteAllVOs(CityVO.class);

		DictionarySearchModuleTestUIAsyncHelper<CityVO> search = openSearch(DemoDictionaryIDs.CITY);
		search.assertTitle("CitySearch (0 results)");

		DictionaryEditorModuleTestUIAsyncHelper<CityVO> editor = openEditor(DemoDictionaryIDs.CITY);
		editor.assertTitle("CityEditor (New)");

		runAsyncTests();
	}
}
