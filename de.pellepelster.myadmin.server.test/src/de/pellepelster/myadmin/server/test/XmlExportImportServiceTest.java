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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;
import de.pellepelster.myadmin.client.web.entities.dictionary.ClientVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminGroupVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.server.core.query.ServerGenericFilterBuilder;
import de.pellepelster.myadmin.server.services.xml.XmlVOExportImportService;
import de.pellepelster.myadmin.server.services.xml.XmlVOExporter;
import de.pellepelster.myadmin.server.services.xml.XmlVOImporter;
import de.pellepelster.myadmin.server.test.base.BaseMyAdminJndiContextTest;

public final class XmlExportImportServiceTest extends BaseMyAdminJndiContextTest
{

	@Autowired
	private XmlVOImporter xmlVOImporter;

	@Autowired
	private XmlVOExporter xmlVOExporter;

	@Autowired
	private XmlVOExportImportService exportImportService;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Before
	public void clearTestData()
	{
		this.baseEntityService.deleteAll(DictionaryVO.class.getName());
		this.baseEntityService.deleteAll(DictionaryControlVO.class.getName());
		this.baseEntityService.deleteAll(MyAdminUserVO.class.getName());
		this.baseEntityService.deleteAll(MyAdminGroupVO.class.getName());
		this.baseEntityService.deleteAll(ClientVO.class.getName());
	}

	@Test
	public void testSimpleXmlExportImport()
	{
		ClientVO client1 = new ClientVO();
		client1.setName("client1");

		ClientVO client2 = new ClientVO();
		client2.setName("client2");

		List<ClientVO> clientVOs = new ArrayList<ClientVO>();
		clientVOs.add(client1);
		clientVOs.add(client2);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		this.xmlVOExporter.exportVOs(outputStream, clientVOs, null);

		clearTestData();

		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

		this.xmlVOImporter.importVOs(inputStream, null);

		clientVOs = this.baseEntityService.filter(ServerGenericFilterBuilder.createGenericFilter(ClientVO.class).getGenericFilter());
		Assert.assertEquals(2, clientVOs.size());

		Assert.assertEquals("client1", clientVOs.get(0).getName());
		Assert.assertEquals("client2", clientVOs.get(1).getName());

	}

	@Test
	public void testSimpleXmlImport()
	{
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ClientTest.xml");

		this.xmlVOImporter.importVOs(inputStream, null);

		List<ClientVO> result = this.baseEntityService.filter(ServerGenericFilterBuilder.createGenericFilter(ClientVO.class).getGenericFilter());

		Assert.assertEquals(2, result.size());

		Assert.assertEquals("client1", result.get(0).getName());
		// Assert.assertEquals(1, result.get(0).getId());

		Assert.assertEquals("client2", result.get(1).getName());
		// Assert.assertEquals(2, result.get(1).getId());
	}

	@Test
	public void testReferencedXmlImport()
	{
		this.xmlVOImporter.importVOs(getClass().getClassLoader().getResourceAsStream("ClientTest.xml"), null);
		this.xmlVOImporter.importVOs(getClass().getClassLoader().getResourceAsStream("MyAdminUserTest.xml"), null);
		this.xmlVOImporter.importVOs(getClass().getClassLoader().getResourceAsStream("MyAdminGroupTest.xml"), null);

		List<MyAdminUserVO> result = this.baseEntityService.filter(ServerGenericFilterBuilder.createGenericFilter(MyAdminUserVO.class).getGenericFilter());

		Assert.assertEquals(2, result.size());

		Assert.assertEquals("user1", result.get(0).getUserName());
		Assert.assertEquals("client1", result.get(0).getClient().getName());

		Assert.assertEquals("user2", result.get(1).getUserName());
	}

	@Test
	public void testReferencedXmlExportImport()
	{
		ClientVO client1 = new ClientVO();
		client1.setName("client1");

		DictionaryVO dictionary1 = new DictionaryVO();
		dictionary1.setName("dictionary1");
		dictionary1.setClient(client1);

		DictionaryControlVO control1 = new DictionaryControlVO();
		control1.setName("control1");
		control1 = this.baseEntityService.create(control1);

		DictionaryControlVO control2 = new DictionaryControlVO();
		control2.setName("control2");
		control2 = this.baseEntityService.create(control2);

		dictionary1.getControlAggregates().add(control1);
		dictionary1.getControlAggregates().add(control2);

		dictionary1 = this.baseEntityService.create(dictionary1);

		File exportDir = new File("/tmp/test");
		exportDir.mkdirs();

		this.exportImportService.exportVOs(ClientVO.class, exportDir);
		this.exportImportService.exportVOs(DictionaryVO.class, exportDir);
		this.exportImportService.exportVOs(DictionaryControlVO.class, exportDir);

		this.baseEntityService.deleteAll(DictionaryVO.class.getName());
		this.baseEntityService.deleteAll(DictionaryControlVO.class.getName());
		this.baseEntityService.deleteAll(ClientVO.class.getName());

		Assert.assertEquals(0, this.baseEntityService.filter(ClientGenericFilterBuilder.createGenericFilter(DictionaryVO.class).getGenericFilter()).size());
		Assert.assertEquals(0, this.baseEntityService.filter(ClientGenericFilterBuilder.createGenericFilter(DictionaryControlVO.class).getGenericFilter())
				.size());
		Assert.assertEquals(0, this.baseEntityService.filter(ClientGenericFilterBuilder.createGenericFilter(ClientVO.class).getGenericFilter()).size());

		this.exportImportService.importVOs(exportDir);

		List<ClientVO> clientResult = this.baseEntityService.filter(ClientGenericFilterBuilder.createGenericFilter(ClientVO.class).getGenericFilter());
		Assert.assertEquals(1, clientResult.size());
		Assert.assertEquals(client1.getName(), clientResult.get(0).getName());

		List<DictionaryVO> dictionaryResult = this.baseEntityService.filter(ClientGenericFilterBuilder.createGenericFilter(DictionaryVO.class)
				.getGenericFilter());
		Assert.assertEquals(1, dictionaryResult.size());
		Assert.assertEquals(dictionary1.getName(), dictionaryResult.get(0).getName());
		Assert.assertEquals(client1.getName(), dictionaryResult.get(0).getClient().getName());
		Assert.assertEquals(2, dictionaryResult.get(0).getControlAggregates().size());

	}

	@Test
	public void testXmlContentDetection()
	{
		String clientsXmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ClientList><Client><name>client1</name></Client></ClientList>";
		Assert.assertEquals(ClientVO.class, this.exportImportService.detectVOClass(clientsXmlString));
	}

	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

	public void setXmlVOImporter(XmlVOImporter xmlVOImporter)
	{
		this.xmlVOImporter = xmlVOImporter;
	}

	public void setXmlVOExporter(XmlVOExporter xmlVOExporter)
	{
		this.xmlVOExporter = xmlVOExporter;
	}

}
