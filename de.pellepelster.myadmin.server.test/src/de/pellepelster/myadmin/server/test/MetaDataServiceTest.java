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
package de.pellepelster.myadmin.server.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.mobile.web.entities.dictionary.DictionarySearchMobileVO;
import de.pellepelster.myadmin.server.services.vo.VOMetaDataService;
import de.pellepelster.myadmin.server.test.base.BaseMyAdminJndiContextTest;

public class MetaDataServiceTest extends BaseMyAdminJndiContextTest
{

	@Autowired
	private VOMetaDataService metaDataService;

	public void setMetaDataService(VOMetaDataService metaDataService)
	{
		this.metaDataService = metaDataService;
	}

	@Test
	public void testGetVOClasses()
	{
		List<Class<? extends IBaseVO>> voClasses = metaDataService.getVOClasses();

		Assert.assertFalse(voClasses.isEmpty());
		Assert.assertTrue(voClasses.contains(DictionaryControlVO.class));
		Assert.assertFalse(voClasses.contains(DictionarySearchMobileVO.class));
	}

}
