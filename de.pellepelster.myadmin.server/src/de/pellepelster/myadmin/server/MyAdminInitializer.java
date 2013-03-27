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
package de.pellepelster.myadmin.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyAdminInitializer implements ServletContextListener
{

	/** {@inheritDoc} */
	@Override
	public void contextDestroyed(ServletContextEvent arg0)
	{
	}

	/** {@inheritDoc} */
	@Override
	public void contextInitialized(ServletContextEvent arg0)
	{

	}

}