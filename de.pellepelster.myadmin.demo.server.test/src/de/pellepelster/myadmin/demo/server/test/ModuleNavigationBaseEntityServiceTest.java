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
package de.pellepelster.myadmin.demo.server.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;

public class ModuleNavigationBaseEntityServiceTest extends BaseDemoTest
{

	@Autowired
	private IBaseEntityService baseEntityService;

	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

	@Test
	public void testCreateNavigation()
	{

		ModuleNavigationVO rootNavigationVO = new ModuleNavigationVO();
		rootNavigationVO.setLabel("root");

		ModuleNavigationVO child1NavigationVO = new ModuleNavigationVO();
		child1NavigationVO.setLabel("child1");
		rootNavigationVO.getChildren().add(child1NavigationVO);

		ModuleNavigationVO child2NavigationVO = new ModuleNavigationVO();
		child2NavigationVO.setLabel("child1");
		child1NavigationVO.getChildren().add(child2NavigationVO);

		this.baseEntityService.create(rootNavigationVO);

	}

	@Test
	public void testGetNavigation()
	{

		GenericFilterVO<ModuleNavigationVO> genericFilterVO = new GenericFilterVO<ModuleNavigationVO>(ModuleNavigationVO.class);
		genericFilterVO.addCriteria(ModuleNavigationVO.FIELD_PARENT.getAttributeName(), null);
		genericFilterVO.addAssociation(ModuleNavigationVO.FIELD_CHILDREN.getAttributeName());
		genericFilterVO.addAssociation(ModuleNavigationVO.FIELD_MODULE).addAssociation(ModuleVO.FIELD_MODULEDEFINITION);

		List<ModuleNavigationVO> result = this.baseEntityService.filter(genericFilterVO);

		assertEquals(1, result.size());

		ModuleNavigationVO root = result.get(0);
		assertEquals("root", root.getLabel());

		ModuleNavigationVO stammdaten = root.getChildren().get(0);
		assertEquals("Stammdaten", stammdaten.getLabel());

		ModuleNavigationVO adresse = stammdaten.getChildren().get(0);
		assertEquals("Adresse", adresse.getLabel());

		ModuleNavigationVO stadt = adresse.getChildren().get(0);
		assertEquals("Stadt", stadt.getLabel());
		Assert.assertNotNull(stadt.getModule());
		Assert.assertNotNull(stadt.getModule().getModuleDefinition());

		assertEquals("DictionarySearch", stadt.getModule().getModuleDefinition().getName());

	}

}
