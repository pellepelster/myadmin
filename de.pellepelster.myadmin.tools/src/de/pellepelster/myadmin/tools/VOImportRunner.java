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
package de.pellepelster.myadmin.tools;

import java.io.File;

import org.apache.log4j.Logger;

import de.pellepelster.myadmin.server.services.xml.XmlVOExportImportService;

public class VOImportRunner
{
	private final static Logger LOGGER = Logger.getLogger(VOImportRunner.class.getSimpleName());

	private final XmlVOExportImportService exportImportService;

	private final File importDir;

	public VOImportRunner(XmlVOExportImportService exportImportService, File importDir)
	{
		this.exportImportService = exportImportService;
		this.importDir = importDir;
	}

	public void run()
	{
		LOGGER.info(String.format("starting vo import from dir '%s'", this.importDir.getPath()));

		try
		{
			this.exportImportService.importVOs(this.importDir);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(String.format("import failed"), e);
		}

	}
}
