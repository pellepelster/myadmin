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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.pellepelster.myadmin.db.test.mockup.entities.Test1;
import de.pellepelster.myadmin.db.test.mockup.entities.Test1.TEST_ENUM;
import de.pellepelster.myadmin.db.test.mockup.entities.Test2;
import de.pellepelster.myadmin.db.test.mockup.entities.Test3;
import de.pellepelster.myadmin.db.test.mockup.vos.Test1VO;
import de.pellepelster.myadmin.db.test.mockup.vos.Test1VO.TEST_ENUM_VO;
import de.pellepelster.myadmin.db.test.mockup.vos.Test2VO;
import de.pellepelster.myadmin.db.test.mockup.vos.Test3VO;
import de.pellepelster.myadmin.db.util.CopyBean;
import de.pellepelster.myadmin.db.util.EntityVOMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/DBTestVOMapper.xml" })
public class CopyBeanTest extends AbstractJUnit4SpringContextTests
{

	private static Test1 test1 = new Test1();

	@BeforeClass
	public static void initData()
	{

		test1 = new Test1();
		test1.setId(1);
		test1.setTestString("String1");
		test1.setTestEnum(TEST_ENUM.ENUM1);

		Test2 test2 = new Test2();
		test2.setTestString("String2");
		test2.setTest1(test1);

		Test3 test3 = new Test3();
		test3.setTestString("String3");
		test2.setTest3(test3);

		test1.getTest2s().add(test2);

	}

	@Test
	public void testEntityVOMapper()
	{

		Assert.assertEquals(Test1VO.class, EntityVOMapper.getInstance().getMappedClass(Test1.class));
		Assert.assertEquals(Test1.class, EntityVOMapper.getInstance().getMappedClass(Test1VO.class));
	}

	@Test
	public void testPartialCopy1()
	{

		Map<Class<?>, List<String>> loadAssociations = new HashMap<Class<?>, List<String>>();

		Test1VO test1VO = (Test1VO) CopyBean.getInstance().copyObject(test1, loadAssociations);

		Assert.assertEquals(1, test1VO.getId());
		Assert.assertEquals(0, test1VO.getTest2s().size());
	}

	@Test
	public void testPartialCopy2()
	{

		Map<Class<?>, List<String>> loadAssociations = new HashMap<Class<?>, List<String>>();
		loadAssociations.put(Test1VO.class, Arrays.asList(new String[] { Test1VO.TEST2S.getAttributeName() }));

		Test1VO test1VO = (Test1VO) CopyBean.getInstance().copyObject(test1, loadAssociations);

		Assert.assertEquals(1, test1VO.getId());
		Assert.assertFalse(test1VO.getTest2s().isEmpty());
	}

	@Test
	public void testSimpleCopy()
	{

		Test1VO test1VO = (Test1VO) CopyBean.getInstance().copyObject(test1, Test1VO.class);

		Assert.assertEquals(1, test1VO.getId());
		Assert.assertEquals(TEST_ENUM_VO.ENUM1, test1VO.getTestEnum());
		Assert.assertEquals("String1", test1VO.getTestString());

		Test2VO test2VO = test1VO.getTest2s().get(0);
		Assert.assertEquals("String2", test2VO.getTestString());

		Test3VO test3VO = test2VO.getTest3();
		Assert.assertEquals("String3", test3VO.getTestString());

	}

}