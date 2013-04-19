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
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;
import de.pellepelster.myadmin.client.web.services.IUserService;

public final class UserServiceTest extends AbstractMyAdminTest
{
	@Autowired
	protected IUserService userService;

	public void setUserService(IUserService userService)
	{
		this.userService = userService;
	}

	@Before
	public void initTestData()
	{
		this.baseVODAO.deleteAll(MyAdminUserVO.class);

		MyAdminUserVO myAdminUser = new MyAdminUserVO();
		myAdminUser.setUserName("peter");
		this.baseVODAO.create(myAdminUser);
	}

	@Test
	public void testCreateUser()
	{
		Assert.assertFalse(this.userService.userNameExists("horst"));
		this.userService.registerUser("horst", "horst@example.org");
		Assert.assertTrue(this.userService.userNameExists("horst"));
	}

	@Test
	public void testUserNameExists()
	{
		Assert.assertFalse(this.userService.userNameExists("xxx"));
		Assert.assertTrue(this.userService.userNameExists("peter"));
	}
}
