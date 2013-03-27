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

public final class ModuleFactoryRegistry
{
	private static ModuleFactoryRegistry instance;

	public static ModuleFactoryRegistry getInstance()
	{
		if (instance == null)
		{
			instance = new ModuleFactoryRegistry();
		}

		return instance;
	}

	private final HashMap<String, IModuleFactory> moduleFactories = new HashMap<String, IModuleFactory>();

	private ModuleFactoryRegistry()
	{
	}

	public void addModuleFactory(String moduleDefinitionName, IModuleFactory moduleFactory)
	{
		moduleFactories.put(moduleDefinitionName, moduleFactory);
	}

	public IModuleFactory getModuleFactory(String moduleDefinitionName)
	{
		return moduleFactories.get(moduleDefinitionName);
	}
}
