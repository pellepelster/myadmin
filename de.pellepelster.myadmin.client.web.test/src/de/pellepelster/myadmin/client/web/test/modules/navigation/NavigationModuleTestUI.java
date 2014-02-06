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

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.layout.BaseModuleUI;
import de.pellepelster.myadmin.client.base.modules.navigation.NavigationTreeElement;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class NavigationModuleTestUI extends BaseModuleUI<Object, ModuleNavigationModule>
{

	public static final String MODULE_ID = NavigationModuleTestUI.class.getName();

	private List<NavigationTreeTestElement> navigationTreeRoots;

	public NavigationModuleTestUI(ModuleNavigationModule module)
	{
		super(module, MODULE_ID);
	}

	@Override
	public boolean close()
	{
		return false;
	}

	@Override
	public boolean contributesToBreadCrumbs()
	{
		return false;
	}

	@Override
	public String getTitle()
	{
		return null;
	}

	public void getRootElements(final AsyncCallback<List<NavigationTreeTestElement>> asyncCallback)
	{
		NavigationModuleTestUI.this.navigationTreeRoots = new ArrayList(Collections2.transform(this.getModule().getNavigationTreeRoots(),
				new Function<NavigationTreeElement, NavigationTreeTestElement>()
				{

					@Override
					public NavigationTreeTestElement apply(NavigationTreeElement input)
					{
						return new NavigationTreeTestElement(input);
					}
				}));

		asyncCallback.onSuccess(NavigationModuleTestUI.this.navigationTreeRoots);
	}

	@Override
	public Object getContainer()
	{
		return null;
	}

}
