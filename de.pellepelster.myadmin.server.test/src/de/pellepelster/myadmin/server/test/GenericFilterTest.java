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
import de.pellepelster.myadmin.client.web.entities.dictionary.ClientVO;
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

		this.baseEntityService.deleteAll(ModuleNavigationVO.class.getName());
		this.baseEntityService.deleteAll(ModuleVO.class.getName());
		this.baseEntityService.deleteAll(ModuleDefinitionVO.class.getName());

		ClientVO client = new ClientVO();
		client.setName("xxx");

		ModuleDefinitionVO moduleDefinition1 = new ModuleDefinitionVO();
		moduleDefinition1.setName("moduledefinition1");
		moduleDefinition1.setClient(client);
		moduleDefinition1 = this.baseEntityService.create(moduleDefinition1);

		ModuleDefinitionVO moduleDefinition2 = new ModuleDefinitionVO();
		moduleDefinition2.setName("moduledefinition2");
		moduleDefinition2.setClient(client);
		moduleDefinition2 = this.baseEntityService.create(moduleDefinition2);

		ModuleVO module1 = new ModuleVO();
		module1.setName("module1");
		module1.setModuleDefinition(moduleDefinition1);
		module1 = this.baseEntityService.create(module1);

		ModuleVO module2 = new ModuleVO();
		module2.setName("module2");
		module2.setModuleDefinition(moduleDefinition2);
		module2 = this.baseEntityService.create(module2);

		ModuleNavigationVO navVO1 = new ModuleNavigationVO();
		navVO1.setTitle("nav1");
		navVO1.setModule(module1);
		navVO1 = this.baseEntityService.create(navVO1);

		ModuleNavigationVO navVO2 = new ModuleNavigationVO();
		navVO2.setTitle("nav2");
		navVO2.setModule(module2);

		navVO2.setParent(navVO1);
		navVO1.getChildren().add(navVO2);
		navVO2 = this.baseEntityService.create(navVO2);

	}

	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

	@Test
	public void testFilterAll()
	{
		Assert.assertEquals(2, this.baseEntityService.filter(GenericFilterFactory.createGenericFilter(ModuleDefinitionVO.class).getGenericFilter()).size());
	}

	@Test
	public void testFilter1()
	{
		ModuleDefinitionVO moduleDefinition = this.baseEntityService.filter(
				GenericFilterFactory.createGenericFilter(ModuleDefinitionVO.class).addCriteria(ModuleDefinitionVO.FIELD_NAME, "moduledefinition1")
						.getGenericFilter()).get(0);

		Assert.assertEquals("moduledefinition1", moduleDefinition.getName());
	}

	@Test
	public void testFilterwithAssociation()
	{
		GenericFilterVO<ModuleVO> genericFilterVO = GenericFilterFactory.createGenericFilter(ModuleVO.class).addCriteria(ModuleVO.FIELD_NAME, "module1")
				.getGenericFilter();
		genericFilterVO.addAssociation(ModuleVO.FIELD_MODULEDEFINITION).addAssociation(ModuleDefinitionVO.FIELD_CLIENT);

		ModuleVO module = this.baseEntityService.filter(genericFilterVO).get(0);

		Assert.assertEquals("module1", module.getName());
		Assert.assertNotNull(module.getModuleDefinition());
		Assert.assertNotNull(module.getModuleDefinition().getClient());
	}

	@Test
	public void testFilterOr()
	{
		GenericFilterVO<ModuleDefinitionVO> genericFilterVO = GenericFilterFactory.createGenericFilter(ModuleDefinitionVO.class).getGenericFilter();

		// , ModuleDefinitionVO.FIELD_NAME, "moduledefinition1"

		List<ModuleDefinitionVO> moduleDefinitions = this.baseEntityService.filter(genericFilterVO);

		Assert.assertEquals("moduledefinition1", moduleDefinitions.get(0).getName());
		Assert.assertEquals("moduledefinition2", moduleDefinitions.get(1).getName());

	}

}
