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

/**
 * Represents the UI for a module instance
 * 
 * @author pelle
 * 
 * @param <WidgetType>
 */
public interface IModuleUI<ContainerType, ModuleType>
{
	static final String UI_MODULE_ID_PARAMETER_NAME = "moduleUIName";

	enum CONTAINER_LAYOUT
	{
		HORIZONTAL, VERTICAL
	}

	int MARGIN = 15;

	int getOrder();

	/**
	 * Try to close the module, returns false if closing fails
	 * 
	 * @return
	 */
	boolean close();

	/**
	 * True if module ui supports breadcrumbs navigation
	 * 
	 * @return
	 */
	boolean contributesToBreadCrumbs();

	/**
	 * The actual UI library specific widget
	 * 
	 * @return
	 */
	ContainerType getContainer();

	/**
	 * The module belonging to this ui
	 * 
	 * @return
	 */
	ModuleType getModule();

	/**
	 * Modules title
	 * 
	 * @return
	 */
	String getTitle();

	boolean isInstanceOf(String moduleUrl);

}
