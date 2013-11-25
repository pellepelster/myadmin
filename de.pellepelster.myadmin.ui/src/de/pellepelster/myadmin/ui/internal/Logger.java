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
package de.pellepelster.myadmin.ui.internal;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

public class Logger
{
	public final static String PLUGIN_ID = Activator.PLUGIN_ID;

	public static final int OK = IStatus.OK;
	public static final int ERROR = IStatus.ERROR;
	public static final int CANCEL = IStatus.CANCEL;
	public static final int INFO = IStatus.INFO;
	public static final int WARNING = IStatus.WARNING;

	private static ILog log = Platform.getLog(Platform.getBundle(PLUGIN_ID));

	public static void error(String message, Throwable exception)
	{
		Status s = new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, message, exception);
		log.log(s);
	}

	public static void error(Throwable exception)
	{
		Status status = new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, null, exception);
		log.log(status);
	}

	public static void warn(String message)
	{
		Status status = new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, message, null);
		log.log(status);
	}

}
