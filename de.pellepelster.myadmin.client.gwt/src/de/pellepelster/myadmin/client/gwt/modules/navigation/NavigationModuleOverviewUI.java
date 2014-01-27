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

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Panel;

import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseModuleUI;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class NavigationModuleOverviewUI extends BaseModuleUI<ModuleNavigationModule>
{

	private final Grid grid;

	/**
	 * @param module
	 */
	public NavigationModuleOverviewUI(ModuleNavigationModule module)
	{
		super(module);

		grid = new Grid(2, 2);
		grid.setWidth("100%");
		grid.setHeight("100%");
		grid.ensureDebugId(ModuleNavigationModule.MODULE_ID);

	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer()
	{
		return grid;
	}

	@Override
	public String getTitle()
	{
		return MyAdmin.MESSAGES.navigationTitle();
	}

}
