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
import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.server.services.ImportExportService;
import de.pellepelster.myadmin.server.services.vo.VOMetaDataService;

public class EntityExportRunner
{
	private final static Logger LOGGER = Logger.getLogger("EntityExport");

	private final ImportExportService importExportService;
	private final VOMetaDataService metaDataService;
	private final File exportDir;

	public EntityExportRunner(ImportExportService importExportService, VOMetaDataService metaDataService, File exportDir)
	{
		this.importExportService = importExportService;
		this.metaDataService = metaDataService;
		this.exportDir = exportDir;
	}

	public void run()
	{
		int logIdentiation = 0;

		LOGGER.info(String.format("starting entity export to dir '%s'", exportDir.getPath()));

		for (Class<? extends IBaseVO> voClass : metaDataService.getVOClasses())
		{
			String xmlFileName = String.format("%s.xml", voClass.getName());

			ToolUtils.logInfo(LOGGER, String.format("exporting '%s' to '%s'", voClass.getName(), xmlFileName), logIdentiation);
			String exportXML = importExportService.exportVO(voClass);

			File xmlFile = new File(exportDir, xmlFileName);

			try
			{
				IOUtils.write(exportXML, new FileOutputStream(xmlFile));
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}

	}
}
