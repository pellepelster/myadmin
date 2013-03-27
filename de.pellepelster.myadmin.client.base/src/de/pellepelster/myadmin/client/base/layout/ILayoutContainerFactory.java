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

import de.pellepelster.myadmin.client.base.module.IModule;

public interface ILayoutContainerFactory<ContainerType> extends IModuleUI<ContainerType, IModule>
{

	/**
	 * Adds a new container to the layout
	 * 
	 * @param layoutContainer
	 */
	void add(int size, IModuleUI<ContainerType, IModule> layoutContainer);

	ILayoutContainerFactory<ContainerType> createContainer(int size, IModuleUI.CONTAINER_LAYOUT containerLayout);
}
