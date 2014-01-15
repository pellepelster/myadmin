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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.log4j.Logger;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.server.services.ImportExportService;
import de.pellepelster.myadmin.server.services.vo.VOMetaDataService;

public class EntityImportRunner
{
	private static final String XML_FILE_EXTENSION = "xml";

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

		LOGGER.info(String.format("starting entity import from dir '%s'", this.importDir.getPath()));

		IOFileFilter xmlFileFilter = FileFilterUtils.suffixFileFilter(XML_FILE_EXTENSION);
		Collection<File> xmlFiles = FileUtils.listFiles(this.importDir, xmlFileFilter, null);

		Map<Class<? extends IBaseVO>, List<File>> filesToImport = new HashMap<Class<? extends IBaseVO>, List<File>>();

		for (File xmlFile : xmlFiles)
		{
			Class<? extends IBaseVO> voClass = this.importExportService.detectVOClass(xmlFile);

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
					ToolUtils.logInfo(LOGGER, String.format("importing '%s' as '%s'", file.toString(), voClass.getName()), logIdentiation);

					try
					{
						this.importExportService.importVO(file);
					}
					catch (Exception e)
					{
						String message = String.format("import of '%s' from '%s' failed", voClass.getName(), file.toString());
						ToolUtils.logInfo(LOGGER, message, logIdentiation);

						throw new RuntimeException(message, e);
					}

				}
			}
		}

	}
}
