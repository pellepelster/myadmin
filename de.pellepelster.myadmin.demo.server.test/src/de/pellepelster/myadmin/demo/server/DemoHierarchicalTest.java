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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.base.jpql.GenericFilterFactory;
import de.pellepelster.myadmin.client.base.modules.hierarchical.HierarchicalConfiguration;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryHierarchicalNodeVO;
import de.pellepelster.myadmin.client.web.services.IHierachicalService;
import de.pellepelster.myadmin.demo.client.web.dictionaries.CompanyDictionaryIDs;
import de.pellepelster.myadmin.demo.client.web.dictionaries.ManagerDictionaryIDs;
import de.pellepelster.myadmin.demo.client.web.entities.CompanyVO;
import de.pellepelster.myadmin.demo.client.web.entities.ManagerVO;
import de.pellepelster.myadmin.dsl.MyAdminDslStandaloneSetup;

public final class DemoHierarchicalTest extends BaseDemoDictionaryImporterTest {
	@Autowired
	private IHierachicalService hierachicalService;

	@Before
	@Override
	public void initData() {
		super.initData();

		MyAdminDslStandaloneSetup.doSetup();

		CompanyVO companyVO1 = new CompanyVO();
		companyVO1.setCompanyName("xxx");
		companyVO1 = getBaseEntityService().create(companyVO1);

		ManagerVO managerVO1 = new ManagerVO();
		managerVO1.setManagerName("aaa");
		managerVO1.setParent(companyVO1);
		getBaseEntityService().create(managerVO1);

		CompanyVO companyVO2 = new CompanyVO();
		companyVO2.setCompanyName("yyy");
		companyVO2 = getBaseEntityService().create(companyVO2);

		ManagerVO managerVO2 = new ManagerVO();
		managerVO2.setManagerName("bbb");
		managerVO2.setParent(companyVO2);
		getBaseEntityService().create(managerVO2);
	}

	public void setHierachicalService(IHierachicalService hierachicalService) {
		this.hierachicalService = hierachicalService;
	}

	@Test
	public void testGetChildren() {
		List<DictionaryHierarchicalNodeVO> rootNodes = hierachicalService.getRootNodes("Test");

		DictionaryHierarchicalNodeVO rootNode1 = rootNodes.get(0);
		Assert.assertEquals(true, rootNode1.getHasChildren());

		List<DictionaryHierarchicalNodeVO> childNodes = hierachicalService.getChildNodes("Test", rootNode1.getVoId(), rootNode1.getVoClassName());

		Assert.assertEquals(1, childNodes.size());
		Assert.assertEquals("aaa", childNodes.get(0).getLabel());
		Assert.assertEquals(false, childNodes.get(0).getHasChildren());
		Assert.assertEquals(ManagerDictionaryIDs.MANAGER, childNodes.get(0).getDictionaryName());
	}

	@Test
	public void testGetHierarchicalConfiguration() {
		HierarchicalConfiguration hierarchicalConfiguration = hierachicalService.getConfigurationById("Test");
		Assert.assertEquals("Test", hierarchicalConfiguration.getId());
	}

	@Test
	public void testGetRootNodes() {
		List<DictionaryHierarchicalNodeVO> rootNodes = hierachicalService.getRootNodes("Test");

		Assert.assertEquals(2, rootNodes.size());

		Assert.assertEquals("xxx", rootNodes.get(0).getLabel());
		Assert.assertEquals("yyy", rootNodes.get(1).getLabel());

		Assert.assertEquals(CompanyDictionaryIDs.COMPANY, rootNodes.get(0).getDictionaryName());
	}

	@Test
	public void testVODecorator() {
		List<DictionaryHierarchicalNodeVO> rootNodes = hierachicalService.getRootNodes("Test");
		DictionaryHierarchicalNodeVO rootNode1 = rootNodes.get(0);

		List<DictionaryHierarchicalNodeVO> childNodes = hierachicalService.getChildNodes("Test", rootNode1.getVoId(), rootNode1.getVoClassName());

		Assert.assertEquals(1, childNodes.size());

		List<ManagerVO> managers = baseVODAO.filter(GenericFilterFactory.createGenericFilter(ManagerVO.class, ManagerVO.FIELD_ID, childNodes.get(0).getVoId()));
		Assert.assertEquals(1, managers.size());
		Assert.assertEquals("xxx", managers.get(0).getParent().get("companyName"));
		Assert.assertEquals(false, managers.get(0).hasChildren());

		List<CompanyVO> companies = baseVODAO.filter(GenericFilterFactory.createGenericFilter(CompanyVO.class));
		Assert.assertEquals(2, companies.size());
		Assert.assertEquals(true, companies.get(0).hasChildren());
		Assert.assertEquals(true, companies.get(1).hasChildren());

	}

}
