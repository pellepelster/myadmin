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

import org.apache.log4j.Logger;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.server.services.vo.VOMetaDataService;
import de.pellepelster.myadmin.server.services.xml.XmlVOExportImportService;

public class EntityExportRunner
{
	private final static Logger LOGGER = Logger.getLogger("EntityExport");

	private final XmlVOExportImportService exportImportService;
	private final VOMetaDataService metaDataService;
	private final File exportDir;

	public EntityExportRunner(XmlVOExportImportService exportImportService, VOMetaDataService metaDataService, File exportDir)
	{
		this.exportImportService = exportImportService;
		this.metaDataService = metaDataService;
		this.exportDir = exportDir;
	}

	public void run()
	{
		int logIdentiation = 0;

		LOGGER.info(String.format("starting entity export to dir '%s'", this.exportDir.getPath()));

		for (Class<? extends IBaseVO> voClass : this.metaDataService.getVOClasses())
		{
			this.exportImportService.exportVOs(voClass, this.exportDir);
		}

	}
}
