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
package de.pellepelster.myadmin.demo.server;

import java.util.UUID;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.pellepelster.myadmin.db.IBaseVODAO;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = { "classpath:/MyAdminServerApplicationContext-gen.xml", "classpath:/MyAdminDemoTestApplicationContext.xml",
		"classpath:/MyAdminDemoServerApplicationContext-gen.xml", "classpath:/MyAdminServerApplicationContext.xml", "classpath:/MyAdminDemoDB-gen.xml" })
public abstract class BaseDemoDBTest extends AbstractTransactionalJUnit4SpringContextTests
{

	public static final String TESTCLIENT_NAME = "testclient";
	public static final String TESTUSER_NAME = "testuser";

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void initJndi() throws Exception
	{

		final SimpleNamingContextBuilder builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		String tempDir = System.getProperty("java.io.tmpdir") + "/ram";

		DataSource ds = new SingleConnectionDataSource("org.apache.derby.jdbc.EmbeddedDriver", String.format("jdbc:derby:%s/myadmin_%s;create=true", tempDir,
				UUID.randomUUID().toString()), "myadmin", "", true);

		builder.bind("java:comp/env/jdbc/MyAdminDemo", ds);

	}

	@Autowired
	protected IBaseVODAO baseVODAO;

	@Before
	public void initSecurityContext()
	{

		// Client client = new Client();
		// client.setName(TESTCLIENT_NAME);
		//
		// User user = new User();
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

	public void setBaseVODAO(IBaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}
}
