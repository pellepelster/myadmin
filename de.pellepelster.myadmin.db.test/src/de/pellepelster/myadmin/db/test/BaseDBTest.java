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

import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import de.pellepelster.myadmin.db.daos.BaseVODAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/DBTestApplicationContext.xml", "classpath:/MyAdminDBApplicationContext.xml" })
@TransactionConfiguration(defaultRollback = false)
@Transactional
@TestExecutionListeners({ TransactionalTestExecutionListener.class })
public abstract class BaseDBTest extends AbstractTransactionalJUnit4SpringContextTests
{

	public static final String TESTCLIENT_NAME = "testclient";
	public static final String TESTUSER_NAME = "testuser";

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void initJndi() throws Exception
	{
		final SimpleNamingContextBuilder builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		String tempDir = System.getProperty("java.io.tmpdir");

		DataSource ds = new SingleConnectionDataSource("org.apache.derby.jdbc.EmbeddedDriver", String.format("jdbc:derby:%s/myadmin_%s;create=true", tempDir,
				UUID.randomUUID().toString()), "myadmin", "", true);
		builder.bind("java:comp/env/jdbc/MyAdminDB", ds);
	}

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public EntityManagerFactory getEntityManagerFactory()
	{
		return this.entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory)
	{
		this.entityManagerFactory = entityManagerFactory;
	}

	@Autowired
	private BaseVODAO baseVODAO;

	public BaseVODAO getBaseVODAO()
	{
		return this.baseVODAO;
	}

	public void setBaseVODAO(BaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

}
