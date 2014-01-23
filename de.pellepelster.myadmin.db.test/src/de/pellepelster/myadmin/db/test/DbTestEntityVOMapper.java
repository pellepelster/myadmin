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
package de.pellepelster.myadmin.db.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import de.pellepelster.myadmin.db.test.mockup.entities.DBTest1;
import de.pellepelster.myadmin.db.test.mockup.entities.DBTest2;
import de.pellepelster.myadmin.db.test.mockup.entities.DBTest3;
import de.pellepelster.myadmin.db.test.mockup.vos.DBTest1VO;
import de.pellepelster.myadmin.db.test.mockup.vos.DBTest2VO;
import de.pellepelster.myadmin.db.test.mockup.vos.DBTest3VO;
import de.pellepelster.myadmin.db.util.BaseEntityVOMapper;
import de.pellepelster.myadmin.db.util.IEntityVOMapper;

@Component
public class DbTestEntityVOMapper extends BaseEntityVOMapper implements IEntityVOMapper
{

	private final Map<Class<?>, Class<?>> entityVOMapper = new HashMap<Class<?>, Class<?>>()
	{
		private static final long serialVersionUID = -2583464835415716667L;
		{
			put(DBTest1VO.class, DBTest1.class);
			put(DBTest1.class, DBTest1VO.class);

			put(DBTest2VO.class, DBTest2.class);
			put(DBTest2.class, DBTest2VO.class);

			put(DBTest3VO.class, DBTest3.class);
			put(DBTest3.class, DBTest3VO.class);

		}
	};

	/** {@inheritDoc} */
	@Override
	protected Map<Class<?>, Class<?>> getClassMappings()
	{
		return this.entityVOMapper;
	}

	@Override
	public Class<?> getMappedClass(Class<?> clazz)
	{
		return this.entityVOMapper.get(clazz);
	}

}
