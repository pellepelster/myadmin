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

import org.junit.Assert;
import org.junit.Test;

import de.pellepelster.myadmin.client.web.entities.dictionary.ClientVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.db.util.CopyBean;
import de.pellepelster.myadmin.server.entities.dictionary.Module;
import de.pellepelster.myadmin.server.entities.dictionary.ModuleNavigation;
import de.pellepelster.myadmin.server.test.base.BaseMyAdminJndiContextTest;

public final class CopyBeanTest extends BaseMyAdminJndiContextTest
{

	@Test
	public void testClone()
	{
		ModuleNavigationVO moduleNavigationVO1 = new ModuleNavigationVO();
		moduleNavigationVO1.setLabel("xxx");

		ModuleNavigationVO moduleNavigationVO2 = new ModuleNavigationVO();
		moduleNavigationVO2.setLabel("yyy");
		moduleNavigationVO1.getChildren().add(moduleNavigationVO2);

		ClientVO client = new ClientVO();
		client.setName("zzz");
		moduleNavigationVO1.setClient(client);

		ModuleNavigationVO clonedNavigationVO = moduleNavigationVO1.cloneVO();

		Assert.assertEquals("xxx", clonedNavigationVO.getLabel());
		Assert.assertTrue(moduleNavigationVO2 != clonedNavigationVO.getChildren().get(0));
		Assert.assertEquals("yyy", clonedNavigationVO.getChildren().get(0).getLabel());
		Assert.assertTrue(client != clonedNavigationVO.getClient());
		Assert.assertEquals("zzz", clonedNavigationVO.getClient().getName());
	}

	@Test
	public void testMap()
	{
		ModuleVO moduleVO = new ModuleVO();
		moduleVO.getProperties().put("foo", "bar");

		Module module = (Module) CopyBean.getInstance().copyObject(moduleVO);

		Assert.assertNotNull(module);

		// Assert.assertEquals("bar", module.getProperties().get("foo"));
	}

	@Test
	public void testSelfReference()
	{
		ModuleNavigationVO navVO1 = new ModuleNavigationVO();
		ModuleNavigationVO navVO2 = new ModuleNavigationVO();
		navVO2.setParent(navVO1);
		navVO1.getChildren().add(navVO2);

		ModuleNavigation eNavVO1 = (ModuleNavigation) CopyBean.getInstance().copyObject(navVO1);
		Assert.assertNotNull(eNavVO1);

		Assert.assertEquals(1, eNavVO1.getChildren().size());
		Assert.assertEquals(eNavVO1.getChildren().get(0).getParent(), eNavVO1);
		Assert.assertEquals(0, eNavVO1.getChildren().get(0).getChildren().size());
	}
}
