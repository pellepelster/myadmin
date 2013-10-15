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

import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class NavigationModuleTestUI implements IModuleUI
{
	private Collection<NavigationTreeTestElement> rootElements;

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
	public Object getContainer()
	{
		return null;
	}

	@Override
	public Object getModule()
	{
		return null;
	}

	@Override
	public String getTitle()
	{
		return null;
	}

	public void get()
	{
		this.module.getNavigationTreeContent(new AsyncCallback<List<ModuleNavigationVO>>()
		{

			@Override
			public void onSuccess(List<ModuleNavigationVO> children)
			{

				NavigationModuleTestUI.this.rootElements = NavigationTreeTestElement.createChildren(children);
			}

			@Override
			public void onFailure(Throwable caught)
			{
				// TODO Auto-generated method stub

			}
		});
	}

}
