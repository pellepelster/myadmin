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
package de.pellepelster.myadmin.demo.server.test.dictionary;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.base.db.vos.Result;
import de.pellepelster.myadmin.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryHierarchicalNodeVO;
import de.pellepelster.myadmin.client.web.services.IHierachicalService;
import de.pellepelster.myadmin.demo.client.TestClientHierarchicalConfiguration;
import de.pellepelster.myadmin.demo.client.web.dictionaries.DemoDictionaryModel;
import de.pellepelster.myadmin.demo.client.web.entities.CompanyVO;
import de.pellepelster.myadmin.demo.client.web.entities.ManagerVO;
import de.pellepelster.myadmin.dsl.MyAdminDslStandaloneSetup;

public final class DemoHierarchicalTest extends BaseDemoDictionaryTest
{
	@Autowired
	private IHierachicalService hierachicalService;

	@Before
	@Override
	public void init()
	{
		super.init();

		getBaseEntityService().deleteAll(CompanyVO.class.getName());

		MyAdminDslStandaloneSetup.doSetup();

		CompanyVO companyVO1 = new CompanyVO();
		companyVO1.setCompanyName("xxx");
		companyVO1 = getBaseEntityService().create(companyVO1);

		ManagerVO managerVO1 = new ManagerVO();
		managerVO1.setName("aaa");
		managerVO1.setParent(companyVO1);
		getBaseEntityService().create(managerVO1);

		CompanyVO companyVO2 = new CompanyVO();
		companyVO2.setCompanyName("yyy");
		companyVO2 = getBaseEntityService().create(companyVO2);

		ManagerVO managerVO2 = new ManagerVO();
		managerVO2.setName("bbb");
		managerVO2.setParent(companyVO2);
		getBaseEntityService().create(managerVO2);
	}

	public void setHierachicalService(IHierachicalService hierachicalService)
	{
		this.hierachicalService = hierachicalService;
	}

	@Test
	public void testRemoveParent()
	{

		CompanyVO companyVO1 = new CompanyVO();
		companyVO1.setCompanyName("xxx");
		companyVO1 = getBaseEntityService().create(companyVO1);

		ManagerVO managerVO1 = new ManagerVO();
		managerVO1.setName("aaa");
		managerVO1.setParent(companyVO1);

		managerVO1 = getBaseEntityService().create(managerVO1);

		Assert.assertNotNull(managerVO1.getParent());

		managerVO1.setParent(null);
		managerVO1 = getBaseEntityService().create(managerVO1);

		Assert.assertNull(managerVO1.getParent());
		Assert.assertNull(managerVO1.getParentClassName());
		Assert.assertNull(managerVO1.getParentId());
	}

	@Test
	public void testGetChildren()
	{
		List<DictionaryHierarchicalNodeVO> rootNodes = this.hierachicalService.getRootNodes(TestClientHierarchicalConfiguration.ID);

		DictionaryHierarchicalNodeVO rootNode1 = rootNodes.get(0);
		Assert.assertEquals(true, rootNode1.getHasChildren());

		List<DictionaryHierarchicalNodeVO> childNodes = this.hierachicalService.getChildNodes(TestClientHierarchicalConfiguration.ID, rootNode1.getVoId(),
				rootNode1.getVoClassName());

		Assert.assertEquals(1, childNodes.size());
		Assert.assertEquals("aaa", childNodes.get(0).getLabel());
		Assert.assertEquals(false, childNodes.get(0).getHasChildren());
		Assert.assertEquals(DemoDictionaryModel.MANAGER.getId(), childNodes.get(0).getDictionaryName());
	}

	@Test
	public void testGetHierarchicalConfiguration()
	{
		HierarchicalConfigurationVO hierarchicalConfiguration = this.hierachicalService.getConfigurationById(TestClientHierarchicalConfiguration.ID);
		Assert.assertEquals(TestClientHierarchicalConfiguration.ID, hierarchicalConfiguration.getId());
	}

	@Test
	public void testGetRootNodes()
	{
		List<DictionaryHierarchicalNodeVO> rootNodes = this.hierachicalService.getRootNodes(TestClientHierarchicalConfiguration.ID);

		Assert.assertEquals(2, rootNodes.size());

		Assert.assertEquals("xxx", rootNodes.get(0).getLabel());
		Assert.assertEquals("yyy", rootNodes.get(1).getLabel());

		Assert.assertEquals(DemoDictionaryModel.COMPANY.getId(), rootNodes.get(0).getDictionaryName());
	}

	@Test
	public void testCreate()
	{
		CompanyVO companyVO1 = new CompanyVO();
		companyVO1.setCompanyName("kkk");
		companyVO1 = getBaseEntityService().create(companyVO1);

		ManagerVO managerVO = new ManagerVO();
		managerVO.setName("ppp");
		managerVO.setParent(companyVO1);
		Result<ManagerVO> savedManagerVO = getBaseEntityService().validateAndCreate(managerVO);

		Assert.assertEquals(managerVO.getParent().getOid(), savedManagerVO.getVO().getParent().getOid());
	}

	@Test
	public void testSave()
	{
		CompanyVO companyVO1 = new CompanyVO();
		companyVO1.setCompanyName("kkk");
		companyVO1 = getBaseEntityService().create(companyVO1);

		ManagerVO managerVO = new ManagerVO();
		managerVO.setName("ppp");
		managerVO.setParent(companyVO1);

		managerVO = getBaseEntityService().create(managerVO);
		managerVO.setName("nnn");

		Result<ManagerVO> savedManagerVO = getBaseEntityService().validateAndSave(managerVO);

		Assert.assertEquals(managerVO.getParent().getOid(), savedManagerVO.getVO().getParent().getOid());
	}

	@Test
	public void testVODecorator()
	{
		List<DictionaryHierarchicalNodeVO> rootNodes = this.hierachicalService.getRootNodes(TestClientHierarchicalConfiguration.ID);
		DictionaryHierarchicalNodeVO rootNode1 = rootNodes.get(0);

		List<DictionaryHierarchicalNodeVO> childNodes = this.hierachicalService.getChildNodes(TestClientHierarchicalConfiguration.ID, rootNode1.getVoId(),
				rootNode1.getVoClassName());

		Assert.assertEquals(1, childNodes.size());

		List<ManagerVO> managers = this.baseVODAO.filter(ClientGenericFilterBuilder.createGenericFilter(ManagerVO.class)
				.addCriteria(ManagerVO.FIELD_ID, childNodes.get(0).getVoId()).getGenericFilter());
		Assert.assertEquals(1, managers.size());
		Assert.assertEquals("xxx", managers.get(0).getParent().get("companyName"));
		Assert.assertEquals(false, managers.get(0).getHasChildren());

		List<CompanyVO> companies = this.baseVODAO.filter(ClientGenericFilterBuilder.createGenericFilter(CompanyVO.class).getGenericFilter());
		Assert.assertEquals(2, companies.size());
		Assert.assertEquals(true, companies.get(0).getHasChildren());
		Assert.assertEquals(true, companies.get(1).getHasChildren());

	}

}
