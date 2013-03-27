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
package de.pellepelster.myadmin.demo.server.old;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryVO;
import de.pellepelster.myadmin.db.util.EntityVOMapper;
import de.pellepelster.myadmin.demo.client.web.entities.CountryVO;
import de.pellepelster.myadmin.demo.server.entities.Country;
import de.pellepelster.myadmin.server.entities.dictionary.Dictionary;

public final class EntityVOMapperTest extends BaseMyAdminDemoDBTest
{
	@Test
	public void testInvalidClass()
	{
		assertNull(EntityVOMapper.getInstance().getMappedClass(String.class));
	}

	@Test
	public void testMyAdminClasses()
	{
		assertEquals(Dictionary.class, EntityVOMapper.getInstance().getMappedClass(DictionaryVO.class));
		assertEquals(DictionaryVO.class, EntityVOMapper.getInstance().getMappedClass(Dictionary.class));
	}

	@Test
	public void testMyAdminDemoClasses()
	{
		assertEquals(Country.class, EntityVOMapper.getInstance().getMappedClass(CountryVO.class));
		assertEquals(CountryVO.class, EntityVOMapper.getInstance().getMappedClass(Country.class));
	}

}
