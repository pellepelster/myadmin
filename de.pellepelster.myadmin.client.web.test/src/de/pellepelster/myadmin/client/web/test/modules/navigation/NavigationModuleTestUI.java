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

import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.modules.navigation.NavigationTreeElement;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class NavigationModuleTestUI implements IModuleUI<Object, ModuleNavigationModule>
{
	private List<NavigationTreeTestElement> navigationTreeRoots;

	private ModuleNavigationModule module;

	public NavigationModuleTestUI(ModuleNavigationModule module)
	{
		super();
		this.module = module;
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
		NavigationModuleTestUI.this.navigationTreeRoots = new ArrayList(Collections2.transform(this.module.getNavigationTreeRoots(),
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
	public ModuleNavigationModule getModule()
	{
		return this.module;
	}

	@Override
	public Object getContainer()
	{
		return null;
	}

	@Override
	public int getOrder()
	{
		return this.module.getOrder();
	}

}
