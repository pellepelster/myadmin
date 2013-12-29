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
package de.pellepelster.myadmin.client.web.modules.navigation;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.modules.navigation.NavigationTreeElement;
import de.pellepelster.myadmin.client.base.modules.navigation.NavigationTreeProvider;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;

public class ModuleNavigationModule extends de.pellepelster.myadmin.client.web.modules.BaseModuleNavigationModule
{

	public ModuleNavigationModule(ModuleVO moduleVO, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(moduleVO, moduleCallback, parameters);

		getModuleCallback().onSuccess(this);
	}

	public List<NavigationTreeElement> getNavigationTreeRoots()
	{
		return NavigationTreeProvider.getRootNavigationElements();
	}

	@Override
	public String getModuleId()
	{
		return getModuleName();
	}
}
