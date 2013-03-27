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

import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;

/**
 * Interface for a layout strategy
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface IDictionaryLayoutStrategy<ContainerType>
{

	/**
	 * Creates UI for a container model hierarchy
	 * 
	 * @param parent
	 * @param containerModel
	 */
	void createLayout(ContainerType parent, IBaseContainerModel containerModel);

	/**
	 * The {@link ILayoutCallback}
	 * 
	 * @return
	 */
	ILayoutCallback getLayoutCallback();

}
