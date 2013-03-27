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
package de.pellepelster.myadmin.client.gwt.modules.dictionary.container;

import com.google.gwt.view.client.ProvidesKey;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public class BaseVOKeyProvider<VOType extends IBaseVO> implements ProvidesKey<VOType>
{

	@Override
	public Object getKey(VOType item)
	{
		return item;
	}

}
