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
package de.pellepelster.myadmin.server.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.server.core.query.ServerGenericFilterBuilder;

public class ImportExportService
{

	private final static Logger LOG = Logger.getLogger(ImportExportService.class);

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private XmlService xmlService;

	public <T extends IBaseVO> String exportVO(Class<T> voClass)
	{
		LOG.info(String.format("exporting all '%s' vos", voClass.getName()));
		List<T> result = this.baseEntityService.filter(ServerGenericFilterBuilder.createGenericFilter(voClass).getGenericFilter());

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		this.xmlService.export(voClass, result, outputStream);

		return new String(outputStream.toByteArray());

	}

	public void importVO(String xmlString)
	{
		Class<?> xmlClass = this.xmlService.detectXmlClass(xmlString);

		LOG.info(String.format("importing '%s' vos", xmlClass));

		ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlString.getBytes());

		this.xmlService.importXml(xmlClass, inputStream);
	}
}
