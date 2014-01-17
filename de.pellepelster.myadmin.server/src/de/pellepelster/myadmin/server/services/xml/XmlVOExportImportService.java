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
package de.pellepelster.myadmin.server.services.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.server.core.query.ServerGenericFilterBuilder;
import de.pellepelster.myadmin.server.services.vo.VOMetaDataService;

@Component
public class XmlVOExportImportService
{
	private final static Logger LOG = Logger.getLogger(XmlVOExportImportService.class);

	private static final String XML_FILE_EXTENSION = "xml";

	private static final int XML_DETECT_PEEK_SIZE = 1024;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private XmlVOExporter xmlVOExporter;

	@Autowired
	private XmlVOImporter xmlVOImporter;

	@Autowired
	private XmlVOMapper xmlVOMapper;

	@Autowired
	private VOMetaDataService metaDataService;

	public <VOType extends IBaseVO> void exportVOs(Class<VOType> voClass, File exportDir)
	{
		LOG.info(String.format("exporting all '%s' vos", voClass.getName()));

		List<VOType> vosToExport = this.baseEntityService.filter(ServerGenericFilterBuilder.createGenericFilter(voClass).getGenericFilter());

		if (!vosToExport.isEmpty())
		{
			String xmlExportFileName = String.format("%s.xml", voClass.getName());
			File xmlExportFile = new File(exportDir, xmlExportFileName);

			OutputStream outputStream = null;

			try
			{
				outputStream = new FileOutputStream(xmlExportFile);
				this.xmlVOExporter.exportVOs(outputStream, vosToExport);
			}
			catch (FileNotFoundException e)
			{
				throw new RuntimeException(e);
			}
			finally
			{
				IOUtils.closeQuietly(outputStream);
			}
		}
	}

	public void importVO(File file)
	{
		FileInputStream inputStream = null;
		try
		{
			LOG.info(String.format("importing vos from '%s' ", file.toString()));

			inputStream = new FileInputStream(file);
			this.xmlVOImporter.importVOs(inputStream);
		}
		catch (Exception e)
		{
			throw new RuntimeException(String.format("import of '%s' failed", file.toString()), e);
		}
		finally
		{
			IOUtils.closeQuietly(inputStream);
		}
	}

	public void importVOs(File importDir)
	{
		IOFileFilter xmlFileFilter = FileFilterUtils.suffixFileFilter(XML_FILE_EXTENSION);
		Collection<File> xmlFiles = FileUtils.listFiles(importDir, xmlFileFilter, null);

		Map<Class<? extends IBaseVO>, List<File>> filesToImport = new HashMap<Class<? extends IBaseVO>, List<File>>();

		for (File xmlFile : xmlFiles)
		{
			Class<? extends IBaseVO> voClass = detectVOClass(xmlFile);

			if (voClass != null)
			{
				if (!filesToImport.containsKey(voClass))
				{
					filesToImport.put(voClass, new ArrayList<File>());
				}

				filesToImport.get(voClass).add(xmlFile);
			}
		}

		for (Class<? extends IBaseVO> voClass : this.metaDataService.getVOClasses())
		{
			if (filesToImport.containsKey(voClass))
			{
				for (File file : filesToImport.get(voClass))
				{
					importVO(file);

				}
			}
		}

	}

	public Class<? extends IBaseVO> detectVOClass(File xmlFile)
	{
		InputStream inputStream = null;
		try
		{
			inputStream = new FileInputStream(xmlFile);
			byte[] byteBuffer = new byte[XML_DETECT_PEEK_SIZE];
			inputStream.read(byteBuffer);

			return detectVOClass(new String(byteBuffer));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			IOUtils.closeQuietly(inputStream);
		}
	}

	public Class<? extends IBaseVO> detectVOClass(String xmlString)
	{
		Class<? extends IBaseVO> voClass = null;

		try
		{
			Pattern regex = Pattern.compile("</?[a-zA-Z][a-zA-Z0-9]*[^<>]*>");
			Matcher regexMatcher = regex.matcher(xmlString);

			String rootXmlElementName = null;

			while (regexMatcher.find())
			{
				rootXmlElementName = regexMatcher.group();
				break;
			}

			if (rootXmlElementName != null)
			{
				rootXmlElementName = StringUtils.removeEnd(rootXmlElementName, ">");
				rootXmlElementName = StringUtils.removeEnd(rootXmlElementName, "/");
				rootXmlElementName = StringUtils.removeStart(rootXmlElementName, "<");
				rootXmlElementName = rootXmlElementName.split(" ")[0];

				voClass = this.xmlVOMapper.getVOClassForElementName(rootXmlElementName);
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		return voClass;
	}

	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

	public void setXmlVOExporter(XmlVOExporter xmlVOExporter)
	{
		this.xmlVOExporter = xmlVOExporter;
	}

	public void setXmlVOImporter(XmlVOImporter xmlVOImporter)
	{
		this.xmlVOImporter = xmlVOImporter;
	}

	public void setXmlVOMapper(XmlVOMapper xmlVOMapper)
	{
		this.xmlVOMapper = xmlVOMapper;
	}

	public void setMetaDataService(VOMetaDataService metaDataService)
	{
		this.metaDataService = metaDataService;
	}

}