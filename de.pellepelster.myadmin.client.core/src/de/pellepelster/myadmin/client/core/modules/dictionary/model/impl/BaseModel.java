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
package de.pellepelster.myadmin.client.core.modules.dictionary.model.impl;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public abstract class BaseModel implements IBaseModel
{
	private static final long serialVersionUID = -564541020450388681L;

	private IBaseModel parent;

	public BaseModel(IBaseModel parent)
	{
		super();
		this.parent = parent;
	}

	/** {@inheritDoc} */
	@Override
	public IBaseModel getParent()
	{
		return parent;
	}

}
