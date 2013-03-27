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
package de.pellepelster.myadmin.client.base.modules.dictionary.model;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;

/**
 * Model for an editor UI
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface IEditorModel extends IBaseModel
{

	/**
	 * The composite structure describing the editor UI
	 * 
	 * @return
	 */
	ICompositeModel getCompositeModel();

	/**
	 * Returns the title
	 * 
	 * @return
	 */
	String getTitle();

	/**
	 * Fully qualified name of the VO managed by this editor
	 * 
	 * @return
	 */
	String getVOName();
}