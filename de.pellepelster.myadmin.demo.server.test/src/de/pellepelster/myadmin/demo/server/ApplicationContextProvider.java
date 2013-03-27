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
package de.pellepelster.myadmin.demo.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.pellepelster.myadmin.server.core.services.IApplicationContextProvider;

public final class ApplicationContextProvider implements IApplicationContextProvider
{

	private static final ApplicationContextProvider instance = new ApplicationContextProvider();

	public static ApplicationContextProvider getInstance()
	{
		return instance;
	}

	private ClassPathXmlApplicationContext context = null;

	private String[] contextLocations;

	protected ApplicationContextProvider()
	{
	}

	public synchronized ApplicationContext getContext()
	{
		if (context == null)
		{
			Thread currentThread = Thread.currentThread();
			ClassLoader originalClassloader = currentThread.getContextClassLoader();
			try
			{
				currentThread.setContextClassLoader(this.getClass().getClassLoader());
				context = new ClassPathXmlApplicationContext(contextLocations);
			}
			finally
			{
				currentThread.setContextClassLoader(originalClassloader);
			}
		}

		return context;
	}

	public synchronized void init(String[] contextLocations)
	{
		this.contextLocations = contextLocations;
		context = null;
	}
}
