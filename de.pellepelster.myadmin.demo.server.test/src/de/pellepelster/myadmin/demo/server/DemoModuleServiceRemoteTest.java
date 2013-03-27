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
package de.pellepelster.myadmin.demo.server;

import java.util.List;

import org.junit.Test;

import de.pellepelster.myadmin.client.web.MyAdminRemoteServiceLocator;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.BaseDictionarySearchModule;
import de.pellepelster.myadmin.client.web.services.IModuleService;

public class DemoModuleServiceRemoteTest extends BaseRemoteTest
{

	@Override
	public void setUp()
	{
		initApplicationContext();
	}

	@Test
	public void testModules()
	{
		IModuleService moduleService = MyAdminRemoteServiceLocator.getInstance().getModuleService();

		List<ModuleNavigationVO> modules = moduleService.getModulesNavigation();

		assertEquals("root", modules.get(0).getTitle());
		assertEquals("Masterdata", modules.get(0).getChildren().get(0).getTitle());
		assertEquals("Address", modules.get(0).getChildren().get(0).getChildren().get(0).getTitle());

		List<ModuleNavigationVO> address = modules.get(0).getChildren().get(0).getChildren().get(0).getChildren();

		ModuleNavigationVO country = address.get(0);
		assertEquals("Country", country.getTitle());
		assertEquals("DictionarySearch", country.getModule().getModuleDefinition().getName());
		assertEquals("Country", country.getModule().getProperties().get(BaseDictionarySearchModule.SEARCHDICTIONARYNAME_PARAMETER_ID));

		ModuleNavigationVO state = address.get(1);
		assertEquals("State", state.getTitle());

		ModuleNavigationVO city = address.get(2);
		assertEquals("City", city.getTitle());

	}

}
