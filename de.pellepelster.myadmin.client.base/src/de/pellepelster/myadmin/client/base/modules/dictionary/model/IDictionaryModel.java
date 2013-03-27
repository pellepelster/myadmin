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

import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;

/**
 * Represents a model for a generic CRUD-UI for arbitrary {@link IBaseVO}
 * derived VOs
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface IDictionaryModel extends IBaseModel
{

	/**
	 * Model for the editor UI
	 * 
	 * @return
	 */
	IEditorModel getEditorModel();

	/**
	 * The controls that represent the label for the entity represented by this
	 * dictionary
	 * 
	 * @return
	 */
	List<IBaseControlModel> getLabelControls();

	/**
	 * Model for the search UI
	 * 
	 * @return
	 */
	ISearchModel getSearchModel();

	/**
	 * Returns the title
	 * 
	 * @return
	 */
	String getTitle();

	/**
	 * Fully qualified name of the VO managed by this dictionary
	 * 
	 * @return
	 */
	String getVOName();
}
