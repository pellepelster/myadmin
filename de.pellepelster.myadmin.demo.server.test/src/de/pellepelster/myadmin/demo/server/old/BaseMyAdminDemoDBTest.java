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
package de.pellepelster.myadmin.demo.server.old;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.pellepelster.myadmin.db.IBaseDAO;
import de.pellepelster.myadmin.server.entities.dictionary.Client;

@RunWith(SpringJUnit4ClassRunner.class)
// @TransactionConfiguration(transactionManager="transactionManager")
// @Transactional
@ContextConfiguration(locations = { "classpath:/MyAdminServerApplicationContext-gen.xml", "classpath:/MyAdminDemoServerApplicationContext-gen.xml",
		"classpath:/MyAdminDemoDB-gen.xml" })
public abstract class BaseMyAdminDemoDBTest extends AbstractJUnit4SpringContextTests
{

	public static final String TESTCLIENT_NAME = "testclient";
	public static final String TESTUSER_NAME = "testuser";

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void initJndi() throws Exception
	{
		final SimpleNamingContextBuilder builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		DataSource ds = new SingleConnectionDataSource("org.postgresql.Driver", "jdbc:postgresql://localhost/myadmindemo", "postgres", "postgres", true);
		builder.bind("java:comp/env/jdbc/MyAdminDemo", ds);
	}

	@Autowired
	protected IBaseDAO baseDAO;

	@Before
	public void initSecurityContext()
	{

		Client client = new Client();
		client.setName(TESTCLIENT_NAME);

		// UserVO user = new UserVO();
		// user.setClient(client);
		// user.setName(TESTUSER_NAME);
		//
		// UsernamePasswordAuthenticationToken upat = new
		// UsernamePasswordAuthenticationToken(user, "password");
		// MockAuthenticationManager mam = new MockAuthenticationManager(true);
		// Authentication auth = mam.authenticate(upat);
		// SecurityContextHolder.getContext().setAuthentication(auth);
		//
		// User user1 = (User)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// Assert.assertEquals(user1.getClient(), user.getClient());

	}

	public void setBaseDAO(IBaseDAO baseDAO)
	{
		this.baseDAO = baseDAO;
	}
}
