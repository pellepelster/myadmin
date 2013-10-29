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

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.pellepelster.myadmin.db.test.mockup.entities.DBTest1;
import de.pellepelster.myadmin.db.test.mockup.entities.DBTest1.TEST_ENUM;
import de.pellepelster.myadmin.db.test.mockup.entities.DBTest2;
import de.pellepelster.myadmin.db.test.mockup.entities.DBTest3;
import de.pellepelster.myadmin.db.test.mockup.vos.DBTest1VO;
import de.pellepelster.myadmin.db.test.mockup.vos.DBTest1VO.TEST_ENUM_VO;
import de.pellepelster.myadmin.db.test.mockup.vos.DBTest2VO;
import de.pellepelster.myadmin.db.test.mockup.vos.DBTest3VO;
import de.pellepelster.myadmin.db.util.CopyBean;
import de.pellepelster.myadmin.db.util.EntityVOMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/DBTestVOMapper.xml" })
public class CopyBeanTest extends AbstractJUnit4SpringContextTests
{

	private static DBTest1 test1 = new DBTest1();

	@BeforeClass
	public static void initData()
	{

		test1 = new DBTest1();
		test1.setId(1);
		test1.setTestString("String1");
		test1.setTestEnum(TEST_ENUM.ENUM1);

		DBTest2 test2 = new DBTest2();
		test2.setTestString("String2");
		test2.setTest1(test1);

		DBTest3 test3 = new DBTest3();
		test3.setTestString("String3");
		test2.setTest3(test3);

		test1.getTest2s().add(test2);

	}

	@Test
	public void testEntityVOMapper()
	{

		Assert.assertEquals(DBTest1VO.class, EntityVOMapper.getInstance().getMappedClass(DBTest1.class));
		Assert.assertEquals(DBTest1.class, EntityVOMapper.getInstance().getMappedClass(DBTest1VO.class));
	}

	@Test
	public void testPartialCopy1()
	{

		Map<Class<?>, List<String>> loadAssociations = new HashMap<Class<?>, List<String>>();

		DBTest1VO test1VO = (DBTest1VO) CopyBean.getInstance().copyObject(test1, loadAssociations);

		Assert.assertEquals(1, test1VO.getId());
		Assert.assertEquals(0, test1VO.getTest2s().size());
	}

	@Test
	public void testPartialCopy2()
	{

		Map<Class<?>, List<String>> loadAssociations = new HashMap<Class<?>, List<String>>();
		loadAssociations.put(DBTest1VO.class, Arrays.asList(new String[] { DBTest1VO.TEST2S.getAttributeName() }));

		DBTest1VO test1VO = (DBTest1VO) CopyBean.getInstance().copyObject(test1, loadAssociations);

		Assert.assertEquals(1, test1VO.getId());
		Assert.assertFalse(test1VO.getTest2s().isEmpty());
	}

	@Test
	public void testSimpleCopy()
	{

		DBTest1VO test1VO = (DBTest1VO) CopyBean.getInstance().copyObject(test1, DBTest1VO.class);

		Assert.assertEquals(1, test1VO.getId());
		Assert.assertEquals(TEST_ENUM_VO.ENUM1, test1VO.getTestEnum());
		Assert.assertEquals("String1", test1VO.getTestString());

		DBTest2VO test2VO = test1VO.getTest2s().get(0);
		Assert.assertEquals("String2", test2VO.getTestString());

		DBTest3VO test3VO = test2VO.getTest3();
		Assert.assertEquals("String3", test3VO.getTestString());

	}

}