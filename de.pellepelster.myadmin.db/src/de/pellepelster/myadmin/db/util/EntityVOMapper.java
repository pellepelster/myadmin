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

import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public final class EntityVOMapper
{

	private static EntityVOMapper instance;

	public static EntityVOMapper getInstance()
	{
		if (instance == null)
		{
			instance = new EntityVOMapper();
		}

		return instance;
	}

	@Autowired
	private List<IEntityVOMapper> entityVOMappers = new ArrayList<IEntityVOMapper>();

	private EntityVOMapper()
	{
	}

	public Class<?> getMappedClass(Class<?> clazz)
	{
		for (IEntityVOMapper entityVOMapper : entityVOMappers)
		{
			if (entityVOMapper.getMappedClass(clazz) != null)
			{
				return entityVOMapper.getMappedClass(clazz);
			}
		}

		return null;
	}

	public Class<?> getMappedClass(String className)
	{
		try
		{
			return getMappedClass(Class.forName(className));
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(String.format("class '%s' not found", className));
		}
	}

	public List<Class<? extends IBaseVO>> getVOClasses()
	{
		List<Class<? extends IBaseVO>> result = new ArrayList<Class<? extends IBaseVO>>();

		for (IEntityVOMapper entityVOMapper : entityVOMappers)
		{
			result.addAll(entityVOMapper.getVOClasses());
		}

		return result;
	}

	public void setEntityVOMappers(List<IEntityVOMapper> entityVOMappers)
	{
		this.entityVOMappers = entityVOMappers;
	}
}
