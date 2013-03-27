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

import java.util.List;

import de.pellepelster.myadmin.client.base.databinding.IUIObservableValue;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

/**
 * UI for an editor panel
 * 
 * @author Christian Pelster
 * 
 * @param <VOType>
 * @param <ContainerType>
 */
public interface IDictionaryEditorUI<VOType extends IBaseVO, ContainerType>
{

	/**
	 * The container representing the editor
	 * 
	 * @return
	 */
	ContainerType getContainer();

	/**
	 * The list of UI observable values
	 * 
	 * @return
	 */
	List<IUIObservableValue> getUIObservableValues();

}
