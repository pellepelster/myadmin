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
package de.pellepelster.myadmin.client.base.modules.dictionary.model.editor;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseRootModel;

/**
 * Model for an editor UI
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface IEditorModel extends IBaseRootModel
{

	/**
	 * Returns the title
	 * 
	 * @return
	 */
	String getLabel();

}