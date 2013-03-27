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
package de.pellepelster.myadmin.db.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.db.IBaseEntity;

public abstract class BaseEntityVOMapper implements IEntityVOMapper
{

	protected abstract Map<Class<?>, Class<?>> getClassMappings();

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public List<Class<? extends IBaseEntity>> getEntityClasses()
	{
		List<Class<? extends IBaseEntity>> result = new ArrayList<Class<? extends IBaseEntity>>();

		for (Class<?> clazz : getClassMappings().values())
		{
			if (IBaseEntity.class.isAssignableFrom(clazz))
			{
				result.add((Class<? extends IBaseEntity>) clazz);
			}
		}

		return result;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public List<Class<? extends IBaseVO>> getVOClasses()
	{
		List<Class<? extends IBaseVO>> result = new ArrayList<Class<? extends IBaseVO>>();

		for (Class<?> clazz : getClassMappings().values())
		{
			if (IBaseVO.class.isAssignableFrom(clazz))
			{
				result.add((Class<? extends IBaseVO>) clazz);
			}
		}

		return result;
	}

}
