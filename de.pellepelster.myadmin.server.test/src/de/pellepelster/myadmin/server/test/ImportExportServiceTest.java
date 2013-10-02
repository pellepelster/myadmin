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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;
import de.pellepelster.myadmin.client.web.entities.dictionary.ClientVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryVO;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.schema.client.ClientList;
import de.pellepelster.myadmin.server.services.ImportExportService;
import de.pellepelster.myadmin.server.services.xml.XmlImportExportService;
import de.pellepelster.myadmin.server.test.base.BaseMyAdminJndiContextTest;

public final class ImportExportServiceTest extends BaseMyAdminJndiContextTest
{

	@Autowired
	private ImportExportService importExportService;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private XmlImportExportService xmlService;

	@Before
	public void initTestData()
	{
	}

	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

	public void setExportService(ImportExportService exportService)
	{
		this.importExportService = exportService;
	}

	public void setXmlService(XmlImportExportService xmlService)
	{
		this.xmlService = xmlService;
	}

	@Test
	public void testExportImportClient()
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

		String clientXml = this.importExportService.exportVO(ClientVO.class);
		String dictionaryXml = this.importExportService.exportVO(DictionaryVO.class);
		String controlsXml = this.importExportService.exportVO(DictionaryControlVO.class);

		this.baseEntityService.deleteAll(DictionaryVO.class.getName());
		this.baseEntityService.deleteAll(DictionaryControlVO.class.getName());
		this.baseEntityService.deleteAll(ClientVO.class.getName());

		Assert.assertEquals(0, this.baseEntityService.filter(ClientGenericFilterBuilder.createGenericFilter(DictionaryVO.class).getGenericFilter()).size());
		Assert.assertEquals(0, this.baseEntityService.filter(ClientGenericFilterBuilder.createGenericFilter(DictionaryControlVO.class).getGenericFilter())
				.size());
		Assert.assertEquals(0, this.baseEntityService.filter(ClientGenericFilterBuilder.createGenericFilter(ClientVO.class).getGenericFilter()).size());

		this.importExportService.importVO(clientXml);
		this.importExportService.importVO(controlsXml);
		this.importExportService.importVO(dictionaryXml);

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

		Assert.assertEquals(ClientList.class, this.xmlService.detectXmlClass(clientsXmlString));
	}

}
