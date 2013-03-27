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
package de.pellepelster.myadmin.client.base.modules.dictionary.model.controls;

import de.pellepelster.myadmin.client.base.jpql.RelationalOperator;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDatabindingAwareModel;

/**
 * Model for a control element
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface IBaseControlModel extends IBaseModel, IDatabindingAwareModel
{

	static final int DEFAULT_WIDTH_HINT = 16;

	/**
	 * Label for column header
	 * 
	 * @return
	 */
	String getColumnLabel();

	/**
	 * Label for input field
	 * 
	 * @return
	 */
	String getEditorLabel();

	/**
	 * Label for input field
	 * 
	 * @return
	 */
	String getFilterLabel();

	/**
	 * Relational operators allowed when filtering this control
	 * 
	 * @return
	 */
	RelationalOperator[] getRelationalOperators();

	/**
	 * The controls tooltip
	 * 
	 * @return
	 */
	String getToolTip();

	/**
	 * Returns the width hint for this control
	 * 
	 * @return
	 */
	Integer getWidthHint();

	/**
	 * Control content is required
	 * 
	 * @return
	 */
	boolean isMandatory();
}
