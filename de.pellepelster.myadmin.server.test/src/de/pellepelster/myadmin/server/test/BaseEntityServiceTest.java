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

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.Result;
import de.pellepelster.myadmin.client.base.entities.dictionary.DICTIONARY_CONTROL_ALIGNMENT;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.messages.IMessage;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleDefinitionVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.server.test.base.BaseMyAdminJndiContextTest;

public final class BaseEntityServiceTest extends BaseMyAdminJndiContextTest
{

	@Autowired
	private IBaseEntityService baseEntityService;

	@Before
	public void initTestData()
	{

		this.baseEntityService.deleteAll(ModuleNavigationVO.class.getName());

		ModuleDefinitionVO moduleDefinition1 = new ModuleDefinitionVO();
		moduleDefinition1.setName("moduledefinition1");
		moduleDefinition1 = this.baseEntityService.create(moduleDefinition1);

		ModuleDefinitionVO moduleDefinition2 = new ModuleDefinitionVO();
		moduleDefinition2.setName("moduledefinition2");
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
	public void testCreate()
	{
		ModuleDefinitionVO moduleDefinition1 = new ModuleDefinitionVO();
		moduleDefinition1.setName("moduledefinition1");
		moduleDefinition1 = this.baseEntityService.create(moduleDefinition1);

		Assert.assertTrue(IBaseVO.NEW_VO_ID != moduleDefinition1.getId());

	}

	@Test
	public void testNavigationWithAssociation()
	{

		// load with module association
		GenericFilterVO<ModuleNavigationVO> genericFilterVO = new GenericFilterVO<ModuleNavigationVO>(ModuleNavigationVO.class.getName());

		genericFilterVO.addCriteria(ModuleNavigationVO.FIELD_PARENT.getAttributeName(), null);
		genericFilterVO.addAssociation(ModuleNavigationVO.FIELD_MODULE.getAttributeName());
		genericFilterVO.addAssociation(ModuleNavigationVO.FIELD_CHILDREN.getAttributeName());
		List<ModuleNavigationVO> result = this.baseEntityService.filter(genericFilterVO);

		Assert.assertEquals(1, result.size());

		ModuleNavigationVO navigationVO = result.get(0);
		Assert.assertEquals("nav1", navigationVO.getTitle());
		Assert.assertNotNull(navigationVO.getModule());

	}

	@Test
	public void testNavigationWithoutAssociation()
	{

		GenericFilterVO<ModuleNavigationVO> genericFilterVO1 = new GenericFilterVO<ModuleNavigationVO>(ModuleNavigationVO.class.getName());
		genericFilterVO1.addCriteria(ModuleNavigationVO.FIELD_PARENT.getAttributeName(), null);

		List<ModuleNavigationVO> result = this.baseEntityService.filter(genericFilterVO1);

		Assert.assertEquals(1, result.size());

		ModuleNavigationVO navigationVO = result.get(0);
		Assert.assertEquals("nav1", navigationVO.getTitle());
		Assert.assertNotNull(navigationVO.getModule());
	}

	@Test
	public void testValidateAndSaveMandatory()
	{
		MyAdminUserVO user = new MyAdminUserVO();

		Result<MyAdminUserVO> result1 = this.baseEntityService.validateAndSave(user);
		Assert.assertEquals(1, result1.getValidationMessages().size());

		Assert.assertEquals(1, result1.getValidationMessages().size());
		Assert.assertEquals(IMessage.SEVERITY.ERROR, result1.getValidationMessages().get(0).getSeverity());
		Assert.assertEquals("Attribute \"userName\" should not be null for entity \"MyAdminUserVO\"", result1.getValidationMessages().get(0).getMessage());

	}

	@Test
	public void testValidateAndSaveNaturalKeyNegative()
	{
		ModuleDefinitionVO moduleDefinition1 = new ModuleDefinitionVO();
		moduleDefinition1.setName("moduledefinition123");
		Result<ModuleDefinitionVO> result1 = this.baseEntityService.validateAndSave(moduleDefinition1);
		Assert.assertEquals(0, result1.getValidationMessages().size());

		ModuleDefinitionVO moduleDefinition2 = new ModuleDefinitionVO();
		moduleDefinition2.setName("moduledefinition123");

		Result<ModuleDefinitionVO> result2 = this.baseEntityService.validateAndSave(moduleDefinition2);
		Assert.assertEquals(1, result2.getValidationMessages().size());
		Assert.assertTrue(result2.getValidationMessages().get(0).getMessage().toLowerCase().contains("duplicate"));
	}

	@Test
	public void testValidateAndSaveNaturalKeyPositive()
	{
		ModuleDefinitionVO moduleDefinition1 = new ModuleDefinitionVO();
		moduleDefinition1.setName("moduledefinition1");
		Result<ModuleDefinitionVO> result = this.baseEntityService.validateAndSave(moduleDefinition1);

		Assert.assertEquals(1, result.getValidationMessages().size());
		Assert.assertEquals(IMessage.SEVERITY.ERROR, result.getValidationMessages().get(0).getSeverity());
		Assert.assertEquals(ModuleDefinitionVO.FIELD_NAME.getAttributeName(),
				result.getValidationMessages().get(0).getContext().get(IValidationMessage.ATTRIBUTE_CONTEXT_KEY));
		Assert.assertEquals("Duplicate value \"moduledefinition1\" for attribute \"name\" at entity \"ModuleDefinitionVO\"", result.getValidationMessages()
				.get(0).getMessage());
	}

	@Test
	public void testGenericSetterEnumeration()
	{
		DictionaryControlVO dictionaryControlVO = new DictionaryControlVO();
		dictionaryControlVO.set(DictionaryControlVO.FIELD_ALIGNMENT.getAttributeName(), DICTIONARY_CONTROL_ALIGNMENT.LEFT.toString());
		Assert.assertEquals(DICTIONARY_CONTROL_ALIGNMENT.LEFT, dictionaryControlVO.getAlignment());
	}

}
