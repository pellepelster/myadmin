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
package de.pellepelster.myadmin.client.base.layout;

import java.util.Map;

/**
 * Generic layout factory hiding the UI library specifics
 * 
 * @author pelle
 * 
 * @param <WidgetType>
 */
public interface ILayoutFactory<ContainerType, WidgetType>
{
	public static final int AUTO_WIDTH = -1;

	/**
	 * Closes the current module and show the previous one (if there is one)
	 * 
	 * @param location
	 * @return
	 */
	boolean closeAndBack(String location);

	/**
	 * True if breadcrumbs exists for module navigation
	 * 
	 * @param location
	 * @return
	 */
	boolean hasBreadCrumbs(String location);

	/**
	 * Shows a previously started module ui
	 * 
	 * @param moduleUI
	 */
	void showModuleUI(IModuleUI<?, ?> moduleUI);

	IModuleUI<?, ?> getCurrentModule(String location);

	/**
	 * Starts the module UI belonging to a module
	 * 
	 * @param module
	 *            the module
	 * @param location
	 * @param parameters
	 *            arbitrary parameters
	 */
	void startModuleUI(IModuleUI<?, ?> moduleUI, String location, Map<String, Object> parameters);

}
