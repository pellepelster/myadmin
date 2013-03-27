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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminGroupVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;
import de.pellepelster.myadmin.server.user.service.MyAdminUserDetailsService;

public class UserDetailsServiceTest extends AbstractMyAdminTest
{

	@Autowired
	private UserDetailsService userDetailsService;

	@Before
	public void initUsers()
	{

		baseVODAO.deleteAll(MyAdminUserVO.class);

		MyAdminUserVO user1 = new MyAdminUserVO();
		user1.setUserName("horst");
		baseVODAO.create(user1);

		MyAdminUserVO user2 = new MyAdminUserVO();
		user2.setUserName("horst");
		baseVODAO.create(user2);

		MyAdminGroupVO groupVO = new MyAdminGroupVO();
		groupVO.setGroupName("admin");
		groupVO = baseVODAO.create(groupVO);

		MyAdminUserVO user3 = new MyAdminUserVO();
		user3.setUserName("kurt");
		user3.getUserGroups().add(groupVO);
		baseVODAO.create(user3);
	}

	public void setUserDetailsService(UserDetailsService userDetailsService)
	{
		this.userDetailsService = userDetailsService;
	}

	@Test
	public void testLoadUserByUsername()
	{
		UserDetails userDetails = userDetailsService.loadUserByUsername("kurt");
		Assert.assertEquals("kurt", userDetails.getUsername());

		Assert.assertEquals(1, userDetails.getAuthorities().size());
		GrantedAuthority grantedAuthority = userDetails.getAuthorities().iterator().next();
		Assert.assertEquals("admin", grantedAuthority.getAuthority());

	}

	@Test(expected = UsernameNotFoundException.class)
	public void testMultipleExistentUser()
	{
		userDetailsService.loadUserByUsername("horst");
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testNonExistentUser()
	{
		userDetailsService.loadUserByUsername("xxx");
	}

	@Test
	public void testSystemUser()
	{
		UserDetails userDetails = userDetailsService.loadUserByUsername(MyAdminUserDetailsService.SYSTEM_USER_NAME);
		Assert.assertEquals(MyAdminUserDetailsService.SYSTEM_USER_NAME, userDetails.getUsername());
		Assert.assertEquals(MyAdminUserDetailsService.SYSTEM_GROUP_NAME, userDetails.getAuthorities().iterator().next().getAuthority());

	}

}
