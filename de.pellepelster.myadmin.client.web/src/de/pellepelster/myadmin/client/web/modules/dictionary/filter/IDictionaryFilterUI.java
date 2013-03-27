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
package de.pellepelster.myadmin.client.web.modules.dictionary.filter;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;

/**
 * Describes a filter UI
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 * @param <VOType>
 */
public interface IDictionaryFilterUI<VOType extends IBaseVO, ContainerType> {

	/**
	 * Clears the current filter
	 */
	void clearFilter();

	/**
	 * The container representing the filter
	 * 
	 * @return
	 */
	ContainerType getContainer();

	/**
	 * Returns the {@link GenericFilterVO} represented by the current filter
	 * 
	 * @return
	 */
	GenericFilterVO<VOType> getFilter();
}