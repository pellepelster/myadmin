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

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import de.pellepelster.myadmin.client.base.jpql.LogicalOperatorVO;
import de.pellepelster.myadmin.client.base.jpql.OrderClauseVO.ORDER_DIRECTION;
import de.pellepelster.myadmin.client.base.jpql.RelationalOperator;
import de.pellepelster.myadmin.db.jpql.AggregateQuery;
import de.pellepelster.myadmin.db.jpql.AggregateQuery.AGGREGATE_TYPE;
import de.pellepelster.myadmin.db.jpql.CountQuery;
import de.pellepelster.myadmin.db.jpql.DeleteQuery;
import de.pellepelster.myadmin.db.jpql.Join;
import de.pellepelster.myadmin.db.jpql.Join.JOIN_TYPE;
import de.pellepelster.myadmin.db.jpql.SelectQuery;
import de.pellepelster.myadmin.db.test.mockup.entities.Test1;
import de.pellepelster.myadmin.db.test.mockup.entities.Test2;
import de.pellepelster.myadmin.db.test.mockup.vos.Test1VO;

public class JPQLTest extends TestCase
{

	@Test
	public void testAggregate()
	{

		AggregateQuery query = new AggregateQuery(Test1.class, Test1VO.TESTINTEGER, AGGREGATE_TYPE.SUM);
		query.addWhereCondition(Test1VO.TESTSTRING, "abc");

		Assert.assertEquals("SELECT SUM(x0.testInteger) FROM Test1 x0 WHERE x0.testString = 'abc'", query.getJPQL());
	}

	@Test
	public void testDeleteAll()
	{
		DeleteQuery query = new DeleteQuery(Test1.class);
		Assert.assertEquals("DELETE FROM Test1 x0", query.getJPQL());
	}

	@Test
	public void testDeleteByFieldString()
	{
		DeleteQuery query = new DeleteQuery(Test1.class);
		query.addWhereCondition(Test1VO.TESTSTRING, "abc");
		Assert.assertEquals("DELETE FROM Test1 x0 WHERE x0.testString = 'abc'", query.getJPQL());
	}

	@Test
	public void testJoin()
	{
		SelectQuery query = new SelectQuery(Test1.class);
		query.addJoin(Test1VO.TEST2S);

		Assert.assertEquals("SELECT x0 FROM Test1 x0 LEFT JOIN x0.test2s x1", query.getJPQL());
	}

	@Test
	public void testJoin1()
	{

		SelectQuery query = new SelectQuery(Test1.class);
		Join join = query.addJoin(Test1VO.TEST2S);
		join.addJoin(JOIN_TYPE.LEFT, Test2.TEST3);

		Assert.assertEquals("SELECT x0 FROM Test1 x0 LEFT JOIN x0.test2s x1 LEFT JOIN x1.test3 x2", query.getJPQL());
	}

	@Test
	public void testOrderByAsc()
	{
		SelectQuery query = new SelectQuery(Test1.class);
		query.addOrderBy(Test1VO.TESTSTRING, ORDER_DIRECTION.ASC);

		Assert.assertEquals("SELECT x0 FROM Test1 x0 ORDER BY x0.testString", query.getJPQL());
	}

	@Test
	public void testOrderByDesc()
	{
		SelectQuery query = new SelectQuery(Test1.class);
		query.addOrderBy(Test1VO.TESTSTRING, ORDER_DIRECTION.DESC);

		Assert.assertEquals("SELECT x0 FROM Test1 x0 ORDER BY x0.testString DESC", query.getJPQL());
	}

	@Test
	public void testSelectAll()
	{
		SelectQuery query = new SelectQuery(Test1.class);

		Assert.assertEquals("SELECT x0 FROM Test1 x0", query.getJPQL());
	}

	@Test
	public void testSelectCount()
	{

		CountQuery query = new CountQuery(Test1.class);

		Assert.assertEquals("SELECT COUNT(x0) FROM Test1 x0", query.getJPQL());
	}

	@Test
	public void testSelectFieldInteger()
	{

		SelectQuery query = new SelectQuery(Test1.class);
		query.addWhereCondition(Test1VO.TESTINTEGER, 1);

		Assert.assertEquals("SELECT x0 FROM Test1 x0 WHERE x0.testInteger = 1", query.getJPQL());
	}

	@Test
	public void testSelectFieldObjectNull()
	{
		SelectQuery query = new SelectQuery(Test1.class);
		query.addWhereCondition(Test1VO.TESTSTRING, "aaa");
		query.addWhereCondition(Test1VO.TESTSTRING, "bbb");

		Assert.assertEquals("SELECT x0 FROM Test1 x0 WHERE x0.testString = 'aaa' AND x0.testString = 'bbb'", query.getJPQL());
	}

	@Test
	public void testSelectFieldOr()
	{
		SelectQuery query = new SelectQuery(Test1.class, LogicalOperatorVO.OR);
		query.addWhereCondition(Test1VO.TESTSTRING, "aaa");
		query.addWhereCondition(Test1VO.TESTSTRING, "bbb");

		Assert.assertEquals("SELECT x0 FROM Test1 x0 WHERE x0.testString = 'aaa' OR x0.testString = 'bbb'", query.getJPQL());
	}

	@Test
	public void testSelectFieldStringEquals()
	{
		SelectQuery query = new SelectQuery(Test1.class);
		query.addWhereCondition(Test1VO.TESTSTRING, "abc");

		Assert.assertEquals("SELECT x0 FROM Test1 x0 WHERE x0.testString = 'abc'", query.getJPQL());
	}

	@Test
	public void testSelectFieldStringLike()
	{
		SelectQuery query = new SelectQuery(Test1.class);
		query.addWhereCondition(Test1VO.TESTSTRING, "abc", RelationalOperator.LIKE);

		Assert.assertEquals("SELECT x0 FROM Test1 x0 WHERE x0.testString LIKE '%abc%'", query.getJPQL());
	}

	@Test
	public void testSelectFieldStringNamed()
	{
		SelectQuery query = new SelectQuery(Test1.class);
		query.addWhereCondition(Test1VO.TESTINTEGER, 1);

		Assert.assertEquals("SELECT x0 FROM Test1 x0 WHERE x0.testInteger = 1", query.getJPQL());
	}

}
