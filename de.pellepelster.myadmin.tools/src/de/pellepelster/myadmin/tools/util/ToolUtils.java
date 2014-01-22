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
package de.pellepelster.myadmin.tools.util;

import org.apache.log4j.Logger;

/**
 * Utilitites for the dictionary importer
 * 
 * @author pelle
 * 
 */
public class ToolUtils
{

	private static final int LOG_IDENTIATION_DEPTH = 4;

	private static String getLogIdentiation(int logIdentation)
	{
		String result = "";

		for (int i = 0; i <= logIdentation * LOG_IDENTIATION_DEPTH; i++)
		{
			result += " ";
		}

		return result;
	}

	public static void logInfo(Logger logger, String message, int logIdentiation)
	{
		logger.info(getLogIdentiation(logIdentiation) + message);
	}

	private ToolUtils()
	{
	}

}
