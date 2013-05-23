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
package de.pellepelster.myadmin.server.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.base.jpql.GenericFilterFactory;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleDefinitionVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.server.test.base.BaseMyAdminJndiContextTest;

public final class GenericFilterTest extends BaseMyAdminJndiContextTest
{

	@Autowired
	private IBaseEntityService baseEntityService;

	@Before
	public void initTestData()
	{
		baseEntityService.deleteAll(ModuleNavigationVO.class.getName());
		baseEntityService.deleteAll(ModuleVO.class.getName());
		baseEntityService.deleteAll(ModuleDefinitionVO.class.getName());

		ModuleDefinitionVO moduleDefinition1 = new ModuleDefinitionVO();
		moduleDefinition1.setName("moduledefinition1");
		moduleDefinition1 = baseEntityService.create(moduleDefinition1);

		ModuleDefinitionVO moduleDefinition2 = new ModuleDefinitionVO();
		moduleDefinition2.setName("moduledefinition2");
		moduleDefinition2 = baseEntityService.create(moduleDefinition2);

		ModuleVO module1 = new ModuleVO();
		module1.setName("module1");
		module1.setModuleDefinition(moduleDefinition1);
		module1 = baseEntityService.create(module1);

		ModuleVO module2 = new ModuleVO();
		module2.setName("module2");
		module2.setModuleDefinition(moduleDefinition2);
		module2 = baseEntityService.create(module2);

		ModuleNavigationVO navVO1 = new ModuleNavigationVO();
		navVO1.setTitle("nav1");
		navVO1.setModule(module1);
		navVO1 = baseEntityService.create(navVO1);

		ModuleNavigationVO navVO2 = new ModuleNavigationVO();
		navVO2.setTitle("nav2");
		navVO2.setModule(module2);

		navVO2.setParent(navVO1);
		navVO1.getChildren().add(navVO2);
		navVO2 = baseEntityService.create(navVO2);

	}

	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

	@Test
	public void testFilterAll()
	{
		Assert.assertEquals(2, baseEntityService.filter(GenericFilterFactory.createGenericFilter(ModuleDefinitionVO.class)).size());
	}

	@Test
	public void testFilter1()
	{
		ModuleDefinitionVO moduleDefinition = baseEntityService.filter(
				GenericFilterFactory.createGenericFilter(ModuleDefinitionVO.class, ModuleDefinitionVO.FIELD_NAME, "moduledefinition1")).get(0);

		Assert.assertEquals("moduledefinition1", moduleDefinition.getName());
	}

	@Test
	public void testFilterOr()
	{
		GenericFilterVO<ModuleDefinitionVO> genericFilterVO = GenericFilterFactory.createGenericFilter(ModuleDefinitionVO.class);

		// , ModuleDefinitionVO.FIELD_NAME, "moduledefinition1"

		List<ModuleDefinitionVO> moduleDefinitions = baseEntityService.filter(genericFilterVO);

		Assert.assertEquals("moduledefinition1", moduleDefinitions.get(0).getName());
		Assert.assertEquals("moduledefinition2", moduleDefinitions.get(1).getName());

	}

}
