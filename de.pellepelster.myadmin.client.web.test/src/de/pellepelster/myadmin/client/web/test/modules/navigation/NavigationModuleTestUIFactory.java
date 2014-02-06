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
package de.pellepelster.myadmin.client.web.test.modules.navigation;

import java.util.Map;

import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.web.module.BaseModuleUIFactory;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;

public class NavigationModuleTestUIFactory extends BaseModuleUIFactory<Object, ModuleNavigationModule>
{

	@Override
	public IModuleUI<Object, ModuleNavigationModule> getNewInstance(ModuleNavigationModule module, IModuleUI<?, ?> previousModuleUI,
			Map<String, Object> parameters)
	{
		if (supports(module.getModuleUrl(), NavigationModuleTestUI.MODULE_ID))
		{
			return new NavigationModuleTestUI(module);
		}

		return null;
	}

}
