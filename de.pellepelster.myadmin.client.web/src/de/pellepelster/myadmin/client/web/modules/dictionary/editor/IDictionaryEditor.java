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
package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.IValidationMessages;

/**
 * basic dictionary editor funktionality
 * 
 * @author pelle
 * 
 * @param <VOType>
 */
public interface IDictionaryEditor<VOType extends IBaseVO>
{

	IValidationMessages getValidationMessages();

	/**
	 * Loads the VO with the given id into the editor
	 * 
	 * @param voId
	 */
	void load();

	/**
	 * Saves the edited vo
	 */
	void save();

}
