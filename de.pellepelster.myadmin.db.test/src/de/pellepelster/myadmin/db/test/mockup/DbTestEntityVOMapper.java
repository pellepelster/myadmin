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
package de.pellepelster.myadmin.db.test.mockup;

import java.util.HashMap;
import java.util.Map;

import de.pellepelster.myadmin.db.test.mockup.entities.Test1;
import de.pellepelster.myadmin.db.test.mockup.entities.Test2;
import de.pellepelster.myadmin.db.test.mockup.entities.Test3;
import de.pellepelster.myadmin.db.test.mockup.vos.Test1VO;
import de.pellepelster.myadmin.db.test.mockup.vos.Test2VO;
import de.pellepelster.myadmin.db.test.mockup.vos.Test3VO;
import de.pellepelster.myadmin.db.util.BaseEntityVOMapper;
import de.pellepelster.myadmin.db.util.IEntityVOMapper;

public class DbTestEntityVOMapper extends BaseEntityVOMapper implements IEntityVOMapper
{

	private final Map<Class<?>, Class<?>> entityVOMapper = new HashMap<Class<?>, Class<?>>()
	{
		private static final long serialVersionUID = -2583464835415716667L;

		{

			put(Test1VO.class, Test1.class);
			put(Test1.class, Test1VO.class);

			put(Test2VO.class, Test2.class);
			put(Test2.class, Test2VO.class);

			put(Test3VO.class, Test3.class);
			put(Test3.class, Test3VO.class);

		}
	};

	/** {@inheritDoc} */
	@Override
	protected Map<Class<?>, Class<?>> getClassMappings()
	{
		return entityVOMapper;
	}

	@Override
	public Class<?> getMappedClass(Class<?> clazz)
	{
		return entityVOMapper.get(clazz);
	}

}
