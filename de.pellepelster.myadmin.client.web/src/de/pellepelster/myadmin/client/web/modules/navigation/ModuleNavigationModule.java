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
import de.pellepelster.myadmin.client.base.module.ModuleUtils;
import de.pellepelster.myadmin.client.base.modules.navigation.NavigationTreeElement;
import de.pellepelster.myadmin.client.base.modules.navigation.NavigationTreeProvider;
import de.pellepelster.myadmin.client.web.MyAdmin;

public class ModuleNavigationModule extends de.pellepelster.myadmin.client.web.modules.BaseModuleNavigationModule
{

	public final static String MODULE_LOCATOR = ModuleUtils.getBaseModuleUrl(MODULE_ID);

	public static final String NAVIGATION_UI_MODULE_ID = MODULE_ID;

	public static final String NAVIGATION_OVERVIEW_UI_MODULE_ID = NAVIGATION_UI_MODULE_ID + "Overview";

	public static final String getNavigationOverviewModuleLocator()
	{
		return ModuleUtils.getBaseUIModuleUrl(NAVIGATION_OVERVIEW_UI_MODULE_ID);
	}

	public final static Resources RESOURCES = ((Resources) GWT.create(Resources.class));;

	public ModuleNavigationModule(String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(moduleUrl, moduleCallback, parameters);

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
	public String getTitle()
	{
		if (hasParameter(MODULE_TITLE_PARAMETER_ID))
		{
			return this.parameters.get(MODULE_TITLE_PARAMETER_ID).toString();
		}
		else
		{
			return MyAdmin.MESSAGES.navigationTitle();
		}
	}

	@Override
	public boolean isInstanceOf(String moduleUrl)
	{
		return MODULE_ID.equals(ModuleUtils.getModuleId(moduleUrl));
	}

}
