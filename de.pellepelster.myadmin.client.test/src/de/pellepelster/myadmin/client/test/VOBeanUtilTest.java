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
package de.pellepelster.myadmin.client.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.pellepelster.myadmin.client.base.VOBeanUtil;
import de.pellepelster.myadmin.client.web.entities.dictionary.ClientVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminGroupVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;

public class VOBeanUtilTest
{

	@Test
	public void testListGetByAttributes()
	{
		MyAdminUserVO userVO = new MyAdminUserVO();

		MyAdminGroupVO groupVO = new MyAdminGroupVO();
		groupVO.setGroupName("xxx");
		userVO.getUserGroups().add(groupVO);

		Assert.assertEquals("xxx", VOBeanUtil.get(userVO, "userGroups[oid=" + groupVO.getOid() + "]/groupName"));
	}

	@Test
	public void testListGetByIndex()
	{
		MyAdminUserVO userVO = new MyAdminUserVO();

		MyAdminGroupVO groupVO = new MyAdminGroupVO();
		userVO.getUserGroups().add(groupVO);

		Assert.assertEquals(groupVO, VOBeanUtil.get(userVO, "userGroups[0]"));
	}

	@Test
	public void testListGetByIndexNested()
	{
		MyAdminUserVO userVO = new MyAdminUserVO();

		MyAdminGroupVO groupVO = new MyAdminGroupVO();
		groupVO.setGroupName("xxx");
		userVO.getUserGroups().add(groupVO);

		Assert.assertEquals("xxx", VOBeanUtil.get(userVO, "userGroups[0]/groupName"));
	}

	@Test
	public void testListSetByAttributes()
	{
		MyAdminUserVO userVO = new MyAdminUserVO();

		MyAdminGroupVO groupVO = new MyAdminGroupVO();
		groupVO.setGroupName("xxx");
		userVO.getUserGroups().add(groupVO);

		MyAdminGroupVO groupVO1 = new MyAdminGroupVO();
		groupVO1.setGroupName("yyy");

		VOBeanUtil.set(userVO, "userGroups[oid=" + groupVO.getOid() + "]", groupVO1);

		Assert.assertEquals(1, userVO.getUserGroups().size());
		Assert.assertEquals("yyy", userVO.getUserGroups().get(0).getGroupName());
	}

	@Test
	public void testListSetByIndex()
	{
		MyAdminUserVO userVO = new MyAdminUserVO();

		MyAdminGroupVO groupVO = new MyAdminGroupVO();
		groupVO.setGroupName("xxx");
		userVO.getUserGroups().add(groupVO);

		MyAdminGroupVO groupVO1 = new MyAdminGroupVO();
		groupVO1.setGroupName("yyy");

		VOBeanUtil.set(userVO, "userGroups[0]", groupVO1);

		Assert.assertEquals(1, userVO.getUserGroups().size());
		Assert.assertEquals("yyy", userVO.getUserGroups().get(0).getGroupName());
	}

	@Test
	public void testListSetByIndexNested()
	{
		MyAdminUserVO userVO = new MyAdminUserVO();

		MyAdminGroupVO groupVO = new MyAdminGroupVO();
		userVO.getUserGroups().add(groupVO);

		VOBeanUtil.set(userVO, "userGroups[0]/groupName", "yyy");

		Assert.assertEquals(1, userVO.getUserGroups().size());
		Assert.assertEquals("yyy", userVO.getUserGroups().get(0).getGroupName());
	}

	@Test
	public void testListSetNewItem()
	{
		MyAdminUserVO userVO = new MyAdminUserVO();

		MyAdminGroupVO groupVO = new MyAdminGroupVO();
		groupVO.setGroupName("xxx");

		VOBeanUtil.set(userVO, "userGroups[]", groupVO);

		Assert.assertEquals(1, userVO.getUserGroups().size());
		Assert.assertEquals("xxx", userVO.getUserGroups().get(0).getGroupName());
	}

	@Test
	public void testListSetNewItemRoot()
	{
		List<MyAdminGroupVO> groups = new ArrayList<MyAdminGroupVO>();

		MyAdminGroupVO groupVO = new MyAdminGroupVO();
		groupVO.setGroupName("xxx");

		VOBeanUtil.set(groups, "[]", groupVO);

		Assert.assertEquals(1, groups.size());
		Assert.assertEquals("xxx", groups.get(0).getGroupName());
	}

	@Test(expected = RuntimeException.class)
	public void testNestedFail()
	{
		MyAdminUserVO userVO = new MyAdminUserVO();

		ClientVO clientVO = new ClientVO();
		clientVO.setName("yyy");
		userVO.setClient(clientVO);

		Assert.assertEquals("yyy", VOBeanUtil.get(userVO, "name/name"));
	}

	@Test
	public void testNestedGet()
	{
		MyAdminUserVO userVO = new MyAdminUserVO();

		ClientVO clientVO = new ClientVO();
		clientVO.setName("yyy");
		userVO.setClient(clientVO);

		Assert.assertEquals("yyy", VOBeanUtil.get(userVO, "client/name"));
	}

	@Test
	public void testNestedSet()
	{
		MyAdminUserVO userVO = new MyAdminUserVO();
		userVO.setClient(new ClientVO());

		VOBeanUtil.set(userVO, "client/name", "xxx1");
		Assert.assertEquals("xxx1", userVO.getClient().getName());
	}

	@Test
	public void testSimpleGet()
	{
		MyAdminUserVO userVO = new MyAdminUserVO();
		userVO.setUserName("sanne");

		Assert.assertEquals("sanne", VOBeanUtil.get(userVO, "userName"));
	}

	@Test
	public void testSimpleSet()
	{
		MyAdminUserVO userVO = new MyAdminUserVO();
		VOBeanUtil.set(userVO, "userName", "sanne");

		Assert.assertEquals("sanne", userVO.getUserName());
	}

}
