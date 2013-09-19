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
package de.pellepelster.myadmin.tools.dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.log4j.Logger;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.server.services.ImportExportService;
import de.pellepelster.myadmin.server.services.VOMetaDataService;

public class EntityImportRunner
{
	private final static Logger LOGGER = Logger.getLogger("EntityImport");

	private final ImportExportService importExportService;
	private final VOMetaDataService metaDataService;
	private final File importDir;

	public EntityImportRunner(ImportExportService importExportService, VOMetaDataService metaDataService, File importDir)
	{
		this.importExportService = importExportService;
		this.metaDataService = metaDataService;
		this.importDir = importDir;
	}

	public void run()
	{
		int logIdentiation = 0;

		LOGGER.info(String.format("starting entity import from dir '%s'", importDir.getPath()));

		for (Class<? extends IBaseVO> voClass : metaDataService.getVOClasses())
		{
			String xmlFilePrefix = String.format("%s", voClass.getName());

			IOFileFilter xmlFileFilter = FileFilterUtils.prefixFileFilter(xmlFilePrefix);
			Collection<File> xmlFiles = FileUtils.listFiles(importDir, xmlFileFilter, null);

			for (File file : xmlFiles)
			{
				ToolUtils.logInfo(LOGGER, String.format("importing '%s' as '%s'", file.toString(), voClass.getName()), logIdentiation);

				String xmlString;

				try
				{
					FileInputStream fileInputStream = new FileInputStream(file);
					xmlString = IOUtils.toString(fileInputStream);
					importExportService.importVO(xmlString);

				}
				catch (Exception e)
				{
					ToolUtils.logInfo(LOGGER, String.format("import of '%s' from '%s' failed", voClass.getName(), file.toString()), logIdentiation);
					throw new RuntimeException(e);
				}

			}
		}

	}
}
