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
package de.pellepelster.myadmin.client.base.modules.dictionary.model.search;

import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

/**
 * Model for a generic search UI consisting of one or more filters and a result
 * list
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface ISearchModel extends IBaseModel
{
	/**
	 * Models for the filter
	 * 
	 * @return
	 */
	List<IFilterModel> getFilterModels();

	/**
	 * Model for the result UI
	 * 
	 * @return
	 */
	IResultModel getResultModel();

	/**
	 * Returns the title
	 * 
	 * @return
	 */
	String getLabel();

}
