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

import java.io.InputStream;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.base.jpql.GenericFilterFactory;
import de.pellepelster.myadmin.client.web.entities.dictionary.ClientVO;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.schema.client.ClientList;
import de.pellepelster.myadmin.schema.client.ClientType;
import de.pellepelster.myadmin.server.services.XmlService;

public final class XmlServicesTest extends AbstractMyAdminTest
{

	@Autowired
	private XmlService xmlService;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Before
	public void initTestData()
	{
	}

	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

	public void setXmlService(XmlService xmlService)
	{
		this.xmlService = xmlService;
	}

	@Test
	public void testGetVOClassForXmlClass()
	{
		Assert.assertEquals(ClientVO.class, xmlService.getVOClassForXmlClass(ClientType.class));
	}

	@Test
	public void testImportXml()
	{
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ClientTest.xml");

		xmlService.importXml(ClientList.class, inputStream);

		List<ClientVO> result = baseEntityService.filter(GenericFilterFactory.createGenericFilter(ClientVO.class, ClientVO.FIELD_NAME, "client1"));
		Assert.assertEquals(2, result.size());
		
		Assert.assertEquals("client1", result.get(0).getName());
		Assert.assertEquals(1, result.get(0).getId());
		
		Assert.assertEquals("client2", result.get(1).getName());
		Assert.assertEquals(2, result.get(1).getId());
	}

}
