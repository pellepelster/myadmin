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
package de.pellepelster.myadmin.client.base.modules.dictionary.model.containers;

import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;

public interface IBaseContainerModel extends IBaseModel
{
	/**
	 * All child composites
	 * 
	 * @return
	 */
	List<IBaseContainerModel> getChildren();

	/**
	 * All controls for this composite
	 * 
	 * @return
	 */
	List<IBaseControlModel> getControls();

}
