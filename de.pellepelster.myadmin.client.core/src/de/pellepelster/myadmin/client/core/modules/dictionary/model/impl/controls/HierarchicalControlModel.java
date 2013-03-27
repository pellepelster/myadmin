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
package de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;

/**
 * Integer control model
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public class HierarchicalControlModel extends BaseControlModel implements IHierarchicalControlModel
{
	private static final long serialVersionUID = -3352525490269503830L;

	private static final String PARENT_ATTRIBUTE_NAME = "parent";

	public HierarchicalControlModel()
	{
	}

	public HierarchicalControlModel(IBaseModel parent, DictionaryControlVO dictionaryControlVO)
	{
		super(parent, dictionaryControlVO);
	}

	@Override
	public String getHierarchicalId()
	{
		return getDictionaryControlVO().getHierarchicalId();
	}

	@Override
	public String getAttributePath()
	{
		return PARENT_ATTRIBUTE_NAME;
	}

}
