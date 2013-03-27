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

import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ClientVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryEditorVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionarySearchVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryVO;
import de.pellepelster.myadmin.db.test.BaseDBTest;
import de.pellepelster.myadmin.db.util.BeanUtil;

public class BeanUtilTest extends BaseDBTest
{
	@Test
	public void testGetVOAttributeDescriptors()
	{
		Set<Class<? extends IBaseVO>> dependentVOs = BeanUtil.getDependentVOs(DictionaryVO.class);
		Assert.assertEquals(4, dependentVOs.size());

		Assert.assertTrue(dependentVOs.contains(DictionaryEditorVO.class));
		Assert.assertTrue(dependentVOs.contains(DictionarySearchVO.class));
		Assert.assertTrue(dependentVOs.contains(DictionaryControlVO.class));
		Assert.assertTrue(dependentVOs.contains(ClientVO.class));

	}
}