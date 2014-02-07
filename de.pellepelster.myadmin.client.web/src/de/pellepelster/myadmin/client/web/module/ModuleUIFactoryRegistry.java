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

import java.util.ArrayList;
import java.util.List;

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

	private final List<IModuleUIFactory<?, ?>> moduleFactories = new ArrayList<IModuleUIFactory<?, ?>>();

	private ModuleUIFactoryRegistry()
	{
	}

	public void addModuleFactory(IModuleUIFactory<?, ?> moduleFactory)
	{
		this.moduleFactories.add(moduleFactory);
	}

	public IModuleUIFactory getModuleFactory(String moduleUrl)
	{
		for (IModuleUIFactory moduleUIFactory : this.moduleFactories)
		{
			if (moduleUIFactory.supports(moduleUrl))
			{
				return moduleUIFactory;
			}
		}

		throw new RuntimeException("unsupported ui module url '" + moduleUrl + "'");
	}

	public boolean supports(String moduleUrl)
	{
		return getModuleFactory(moduleUrl) != null;
	}
}
