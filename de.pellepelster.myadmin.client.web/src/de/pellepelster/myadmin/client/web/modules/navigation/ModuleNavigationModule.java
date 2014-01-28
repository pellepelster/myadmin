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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.modules.navigation.NavigationTreeElement;
import de.pellepelster.myadmin.client.base.modules.navigation.NavigationTreeProvider;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;

public class ModuleNavigationModule extends de.pellepelster.myadmin.client.web.modules.BaseModuleNavigationModule
{

	public final static Resources RESOURCES = ((Resources) GWT.create(Resources.class));;

	public ModuleNavigationModule(ModuleVO moduleVO, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(moduleVO, moduleCallback, parameters);

		getModuleCallback().onSuccess(this);
	}

	public List<NavigationTreeElement> getNavigationTreeRoots()
	{
		return NavigationTreeProvider.getRootNavigationElements();
	}

	public List<NavigationTreeElement> getChildrenForNavigationElement(String navigationTreeElementName)
	{
		return getNavigationElement(getNavigationTreeRoots(), navigationTreeElementName).getChildren();
	}

	public NavigationTreeElement getNavigationElement(List<NavigationTreeElement> navigationTreeElements, String navigationTreeElementName)
	{
		for (NavigationTreeElement navigationTreeElement : navigationTreeElements)
		{
			if (navigationTreeElement.getName().equals(navigationTreeElementName))
			{
				return navigationTreeElement;
			}

			NavigationTreeElement t = getNavigationElement(navigationTreeElement.getChildren(), navigationTreeElementName);

			if (t != null)
			{
				return t;
			}

		}

		return null;
	}

	@Override
	public String getModuleId()
	{
		return getModuleName();
	}
}
