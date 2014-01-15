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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.server.core.query.ServerGenericFilterBuilder;
import de.pellepelster.myadmin.server.services.xml.XmlImportExportService;

@Component
public class ImportExportService
{

	private final static Logger LOG = Logger.getLogger(ImportExportService.class);

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private XmlImportExportService xmlService;

	public <T extends IBaseVO> String exportVO(Class<T> voClass)
	{
		LOG.info(String.format("exporting all '%s' vos", voClass.getName()));
		List<T> result = this.baseEntityService.filter(ServerGenericFilterBuilder.createGenericFilter(voClass).getGenericFilter());

		if (result.isEmpty())
		{
			return null;
		}
		else
		{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			this.xmlService.export(voClass, result, outputStream);

			return new String(outputStream.toByteArray());
		}
	}

	public void importVO(File file)
	{
		try
		{
			Class<?> xmlClass = this.xmlService.detectXmlClass(file);

			LOG.info(String.format("importing '%s' vos", xmlClass));

			FileInputStream inputStream = new FileInputStream(file);
			this.xmlService.importXml(xmlClass, inputStream);
			inputStream.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException(String.format("import of '%s'", file.toString()));
		}

	}

	public Class<? extends IBaseVO> detectVOClass(File file)
	{
		try
		{
			byte[] byteBuffer = new byte[XmlImportExportService.PEEK_SIZE];
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(byteBuffer);
			fileInputStream.close();

			return this.xmlService.detectVOClass(new String(byteBuffer));
		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

}
