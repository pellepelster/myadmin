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
package de.pellepelster.myadmin.client.gwt.modules.navigation;

import java.util.Map;

import com.google.gwt.user.client.ui.Panel;

import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.web.module.IModuleUIFactory;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;

public class NavigationModuleUIFactory implements IModuleUIFactory<Panel, ModuleNavigationModule>
{

	@Override
	public IModuleUI<Panel, ModuleNavigationModule> getNewInstance(ModuleNavigationModule module, IModuleUI<?, ?> previousModuleUI,
			Map<String, Object> parameters)
	{
		if (module.getNavigationTreeElementName() != null)
		{
			return new NavigationModuleOverviewUI(module);
		}
		else
		{
			return new NavigationModuleTreeUI(module);
		}
	}
}
