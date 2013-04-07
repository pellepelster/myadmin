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

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.db.test.mockup.vos.Test1VO;
import de.pellepelster.myadmin.db.test.mockup.vos.Test2VO;
import de.pellepelster.myadmin.db.test.mockup.vos.Test3VO;

public class BaseVODAOTest extends BaseDBTest
{
	@Before
	public void initData()
	{
		getBaseVODAO().deleteAll(Test1VO.class);
		getBaseVODAO().deleteAll(Test2VO.class);
		getBaseVODAO().deleteAll(Test3VO.class);
	}

	@Test
	public void testCreate()
	{
		Test2VO test2VO = new Test2VO();
		test2VO.setTestString("xxx");

		Test3VO test3VO = new Test3VO();
		test3VO.setTestString("yyy");

		test2VO.setTest3(test3VO);

		getBaseVODAO().deleteAll(Test2VO.class);
		getBaseVODAO().deleteAll(Test3VO.class);
		getBaseVODAO().create(test2VO);

		GenericFilterVO<Test2VO> genericFilterVO = new GenericFilterVO<Test2VO>(Test2VO.class);
		List<Test2VO> result = getBaseVODAO().filter(genericFilterVO);

		Assert.assertEquals(1, result.size());

		Test2VO test2VOResult = result.get(0);
		Assert.assertEquals("xxx", test2VOResult.getTestString());

		Assert.assertNotNull(test2VOResult.getTest3());

		Test3VO test3VOResult = test2VOResult.getTest3();
		Assert.assertEquals("yyy", test3VOResult.getTestString());

	}

	@Test
	public void testCreate1()
	{
		// create test3vo
		Test3VO test3VO = new Test3VO();
		test3VO.setTestString("yyy");
		getBaseVODAO().create(test3VO);

		// get test3vo
		List<Test3VO> tempResult = getBaseVODAO().filter(new GenericFilterVO<Test3VO>(Test3VO.class));

		Test2VO test2VO = new Test2VO();
		test2VO.setTestString("xxx");
		test2VO.setTest3(tempResult.get(0));
		getBaseVODAO().create(test2VO);

		GenericFilterVO<Test2VO> genericFilterVO = new GenericFilterVO<Test2VO>(Test2VO.class);
		List<Test2VO> result = getBaseVODAO().filter(genericFilterVO);

		Assert.assertEquals(1, result.size());

		Test2VO test2VOResult = result.get(0);
		Assert.assertEquals("xxx", test2VOResult.getTestString());

		Assert.assertNotNull(test2VOResult.getTest3());

		Test3VO test3VOResult = test2VOResult.getTest3();
		Assert.assertEquals("yyy", test3VOResult.getTestString());

	}

	@Test
	public void testCreate2()
	{
		getBaseVODAO().deleteAll(Test1VO.class);
		getBaseVODAO().deleteAll(Test2VO.class);
		getBaseVODAO().deleteAll(Test3VO.class);

		// create test3vo
		Test1VO test1VO = new Test1VO();
		test1VO.setTestString("zzz");
		test1VO = getBaseVODAO().create(test1VO);

		Test3VO test3VO = new Test3VO();
		test3VO.setTestString("yyy");
		test3VO.setTest1(test1VO);
		getBaseVODAO().create(test3VO);

		// load test3vo with test1 assoc
		GenericFilterVO<Test3VO> test3VOFilterWithTest1Assoc = new GenericFilterVO<Test3VO>(Test3VO.class);
		test3VOFilterWithTest1Assoc.addAssociation(Test3VO.TEST1);
		List<Test3VO> tempResult = getBaseVODAO().filter(test3VOFilterWithTest1Assoc);
		Assert.assertEquals(1, tempResult.size());
		Test3VO test3VOResult = tempResult.get(0);
		Assert.assertNotNull(test3VOResult.getTest1());

		// load test3vo without test1 assoc (first level attributes always get
		// loaded)
		GenericFilterVO<Test3VO> test3VOFilterWithOutTest1Assoc = new GenericFilterVO<Test3VO>(Test3VO.class);
		tempResult = getBaseVODAO().filter(test3VOFilterWithOutTest1Assoc);
		Assert.assertEquals(1, tempResult.size());
		test3VOResult = tempResult.get(0);
		Assert.assertNotNull(test3VOResult.getTest1());

		// get test3vo
		/*
		 * List<Test3VO> tempResult = getBaseVODAO().filter(new
		 * GenericFilterVO<Test3VO>(Test3VO.class));
		 * 
		 * Test2VO test2VO = new Test2VO(); test2VO.setTestString("xxx");
		 * test2VO.setTest3(tempResult.get(0)); getBaseVODAO().create(test2VO);
		 * 
		 * GenericFilterVO<Test2VO> genericFilterVO = new
		 * GenericFilterVO<Test2VO>(Test2VO.class); List<Test2VO> result =
		 * getBaseVODAO().filter(genericFilterVO);
		 * 
		 * Assert.assertEquals(1, result.size());
		 * 
		 * Test2VO test2VOResult = result.get(0); Assert.assertEquals("xxx",
		 * test2VOResult.getTestString());
		 * 
		 * Assert.assertNotNull(test2VOResult.getTest3());
		 * 
		 * Test3VO test3VOResult = test2VOResult.getTest3();
		 * Assert.assertEquals("yyy", test3VOResult.getTestString());
		 */
	}

	@Test
	public void testCreateWithAssocManual()
	{
		// create test3vo
		Test1VO test1VO = new Test1VO();
		test1VO.setTestString("zzz");
		test1VO = getBaseVODAO().create(test1VO);

		Test3VO test3VO = new Test3VO();
		test3VO.setTestString("yyy");
		test3VO.setTest1(test1VO);
		getBaseVODAO().create(test3VO);

		// load test3vo with test1 assoc
		GenericFilterVO<Test3VO> test3VOFilterWithTest1Assoc = new GenericFilterVO<Test3VO>(Test3VO.class);
		test3VOFilterWithTest1Assoc.addAssociation(Test3VO.TEST1);
		List<Test3VO> tempResult = getBaseVODAO().filter(test3VOFilterWithTest1Assoc);
		Assert.assertEquals(1, tempResult.size());
		Test3VO test3VOResult = tempResult.get(0);
		Assert.assertNotNull(test3VOResult.getTest1());

		// load test3vo without test1 assoc (first level attributes always get
		// loaded)
		GenericFilterVO<Test3VO> test3VOFilterWithOutTest1Assoc = new GenericFilterVO<Test3VO>(Test3VO.class);
		tempResult = getBaseVODAO().filter(test3VOFilterWithOutTest1Assoc);
		Assert.assertEquals(1, tempResult.size());
		test3VOResult = tempResult.get(0);
		Assert.assertNotNull(test3VOResult.getTest1());
	}

	@Test
	public void testDeepCreate()
	{
		// create test3vo
		Test1VO test1VO = new Test1VO();
		test1VO.setTestString("zzz");

		Test3VO test3VO = new Test3VO();
		test3VO.setTestString("yyy");
		test3VO.setTest1(test1VO);
		getBaseVODAO().create(test3VO);

		GenericFilterVO<Test3VO> test3VOFilter = new GenericFilterVO<Test3VO>(Test3VO.class);
		List<Test3VO> tempResult = getBaseVODAO().filter(test3VOFilter);

		Assert.assertEquals(1, tempResult.size());
		Test3VO test3VOResult = tempResult.get(0);
		Assert.assertNotNull(test3VOResult.getTest1());
	}

	@Test
	public void testDeepListCreate()
	{
		// create test3vo
		Test1VO test1VO = new Test1VO();
		test1VO.setTestString("xxx");

		Test3VO test3VO1 = new Test3VO();
		test3VO1.setTestString("aaa");

		Test2VO test2VO1 = new Test2VO();
		test2VO1.setTestString("yyy");
		test1VO.getTest2s().add(test2VO1);
		test2VO1.setTest3(test3VO1);

		Test3VO test3VO2 = new Test3VO();
		test3VO2.setTestString("bbb");

		Test2VO test2VO2 = new Test2VO();
		test2VO2.setTestString("zzz");
		test1VO.getTest2s().add(test2VO2);
		test2VO2.setTest3(test3VO2);

		getBaseVODAO().create(test1VO);

		GenericFilterVO<Test1VO> test1VOFilter = new GenericFilterVO<Test1VO>(Test1VO.class);
		test1VOFilter.addAssociation(Test1VO.TEST2S).addAssociation(Test2VO.TEST3);
		List<Test1VO> tempResult = getBaseVODAO().filter(test1VOFilter);

		Assert.assertEquals(1, tempResult.size());
		Test1VO test1VOResult = tempResult.get(0);

		Assert.assertEquals(2, test1VOResult.getTest2s().size());
		Assert.assertEquals("yyy", test1VOResult.getTest2s().get(0).getTestString());
		Assert.assertEquals("zzz", test1VOResult.getTest2s().get(1).getTestString());

		Assert.assertEquals("aaa", test1VOResult.getTest2s().get(0).getTest3().getTestString());
		Assert.assertEquals("bbb", test1VOResult.getTest2s().get(1).getTest3().getTestString());

	}

	@Test
	public void testListCreate()
	{
		// create test3vo
		Test1VO test1VO = new Test1VO();
		test1VO.setTestString("xxx");

		Test2VO test2VO1 = new Test2VO();
		test2VO1.setTestString("yyy");
		test1VO.getTest2s().add(test2VO1);

		Test2VO test2VO2 = new Test2VO();
		test2VO2.setTestString("zzz");
		test1VO.getTest2s().add(test2VO2);

		getBaseVODAO().create(test1VO);

		GenericFilterVO<Test1VO> test1VOFilter = new GenericFilterVO<Test1VO>(Test1VO.class);
		List<Test1VO> tempResult = getBaseVODAO().filter(test1VOFilter);

		Assert.assertEquals(1, tempResult.size());
		Test1VO test1VOResult = tempResult.get(0);

		Assert.assertEquals(2, test1VOResult.getTest2s().size());
		Assert.assertEquals("yyy", test1VOResult.getTest2s().get(0).getTestString());
		Assert.assertEquals("zzz", test1VOResult.getTest2s().get(1).getTestString());
	}

	@Test
	public void testSimpleCreate()
	{
		Test1VO test1VO = new Test1VO();
		test1VO.setTestString("xxx");
		getBaseVODAO().create(test1VO);

		GenericFilterVO<Test1VO> genericFilterVO = new GenericFilterVO<Test1VO>(Test1VO.class);
		List<Test1VO> result = getBaseVODAO().filter(genericFilterVO);

		Assert.assertEquals(1, result.size());
		Test1VO test1VOResult = result.get(0);
		Assert.assertEquals("xxx", test1VOResult.getTestString());
	}

	@Test
	public void testMapDelete()
	{
		Test1VO test1VO = new Test1VO();
		test1VO.setTestString("xxx");
		test1VO.getMap().put("a", "a");

		getBaseVODAO().create(test1VO);

		GenericFilterVO<Test1VO> genericFilterVO = new GenericFilterVO<Test1VO>(Test1VO.class);
		List<Test1VO> result = getBaseVODAO().filter(genericFilterVO);

		Assert.assertEquals(1, result.size());
		Test1VO test1VOResult = result.get(0);
		Assert.assertEquals("a", test1VOResult.getMap().get("a"));

		getBaseVODAO().deleteAll(Test1VO.class);
	}
}