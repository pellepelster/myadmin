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

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDatabindingAwareModel;

/**
 * Model for a grid control
 * 
 * @author Christian Pelster
 * 
 */
public interface IEditableTableModel extends IBaseContainerModel, IBaseTableModel, IDatabindingAwareModel
{

	/**
	 * Fully qualified name of the VO managed by this grid
	 * 
	 * @return
	 */
	String getVOName();

}
