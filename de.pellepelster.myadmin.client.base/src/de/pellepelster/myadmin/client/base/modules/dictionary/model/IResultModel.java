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

import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;

/**
 * Model for a search result list
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface IResultModel extends IBaseModel
{

	/** Default paging size */
	final static int DEFAULT_PAGING_SIZE = 25;

	/**
	 * Control models representing the columns
	 * 
	 * @return
	 */
	List<IBaseControlModel> getControls();

	/**
	 * Maximum Number of search results to show
	 * 
	 * @return
	 */
	int getMaxResults();

	/**
	 * Number of results per page
	 * 
	 * @return
	 */
	int getPagingSize();
}
