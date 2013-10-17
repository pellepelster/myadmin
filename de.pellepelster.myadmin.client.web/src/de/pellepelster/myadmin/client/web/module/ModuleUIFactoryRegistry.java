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
package de.pellepelster.myadmin.client.web.module;

import java.util.HashMap;

import de.pellepelster.myadmin.client.base.module.IModule;

public final class ModuleUIFactoryRegistry
{
	private static ModuleUIFactoryRegistry instance;

	public static ModuleUIFactoryRegistry getInstance()
	{
		if (instance == null)
		{
			instance = new ModuleUIFactoryRegistry();
		}

		return instance;
	}

	private final HashMap<Class<? extends IModule>, IModuleUIFactory<?, ?>> moduleFactories = new HashMap<Class<? extends IModule>, IModuleUIFactory<?, ?>>();

	private ModuleUIFactoryRegistry()
	{
	}

	public void addModuleFactory(Class<? extends IModule> moduleClass, IModuleUIFactory<?, ?> moduleFactory)
	{
		this.moduleFactories.put(moduleClass, moduleFactory);
	}

	public IModuleUIFactory<?, ?> getModuleFactory(Class<? extends IModule> moduleClass)
	{
		if (this.moduleFactories.containsKey(moduleClass))
		{
			return this.moduleFactories.get(moduleClass);
		}
		else
		{
			throw new RuntimeException("unsupported ui module type '" + moduleClass.getName() + "'");
		}
	}
}
