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

import java.util.Collections;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;
import de.pellepelster.myadmin.db.IBaseVODAO;
import de.pellepelster.myadmin.server.user.service.MyAdminUserDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
@Transactional
@ContextConfiguration(locations = { "classpath:/MyAdminServerTestApplicationContext.xml", "classpath:/MyAdminServerApplicationContext.xml",
		"classpath:/MyAdminServerApplicationContext-gen.xml", "classpath:/MyAdminDB-gen.xml", "classpath:/MyAdminServerSpringSecurity.xml" })
public abstract class AbstractMyAdminTest extends AbstractJUnit4SpringContextTests
{

	public static final String TESTUSER_NAME = "testuser";

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void initJndi() throws Exception
	{
		fakeAuthentication();

		final SimpleNamingContextBuilder builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		String tempDir = System.getProperty("java.io.tmpdir");

		DataSource ds = new SingleConnectionDataSource("org.apache.derby.jdbc.EmbeddedDriver", String.format("jdbc:derby:%s/myadmin_%s;create=true", tempDir,
				UUID.randomUUID().toString()), "myadmin", "", true);
		builder.bind("java:comp/env/jdbc/MyAdmin", ds);
	}

	private static void fakeAuthentication()
	{
		SecurityContextImpl sc = new SecurityContextImpl();

		MyAdminUserVO myAdminUserVO = new MyAdminUserVO();
		myAdminUserVO.setUserName(TESTUSER_NAME);

		Authentication auth = new UsernamePasswordAuthenticationToken(new MyAdminUserDetails(myAdminUserVO), TESTUSER_NAME, Collections.EMPTY_LIST);
		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);
	}

	@Autowired
	protected IBaseVODAO baseVODAO;

	public void setBaseVODAO(IBaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}
}
