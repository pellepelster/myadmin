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

import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class NavigationModuleTestUI implements IModuleUI {

	private ModuleNavigationModule module;

	public NavigationModuleTestUI(ModuleNavigationModule module) {
		super();
		this.module = module;
	}

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contributesToBreadCrumbs() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getModule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

}
