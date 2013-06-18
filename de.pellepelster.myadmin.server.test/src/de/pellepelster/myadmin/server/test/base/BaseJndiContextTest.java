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
package de.pellepelster.myadmin.server.test.base;

import java.util.UUID;

import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import de.pellepelster.myadmin.db.IBaseVODAO;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
@Transactional
@ContextConfiguration(locations = { "classpath:/MyAdminServerTestApplicationContext.xml", "classpath:/MyAdminServerApplicationContext.xml", "classpath:/MyAdminServerApplicationContext-gen.xml",
		"classpath:/MyAdminServerApplicationContextServices-gen.xml", "classpath:/MyAdminDB-gen.xml", "classpath:/MyAdminServerSpringSecurity.xml" })
public abstract class BaseJndiContextTest extends AbstractJUnit4SpringContextTests {

	public static void initJndi(String contextName) throws Exception {
		final SimpleNamingContextBuilder builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		String tempDir = System.getProperty("java.io.tmpdir");

		@SuppressWarnings("deprecation")
		DataSource ds = new SingleConnectionDataSource("org.apache.derby.jdbc.EmbeddedDriver", String.format("jdbc:derby:%s/%s_%s;create=true", tempDir, contextName.toLowerCase(), UUID.randomUUID().toString()), "myadmin", "", true);
		builder.bind(String.format("java:comp/env/jdbc/%s", contextName), ds);
	}

	@Autowired
	protected IBaseVODAO baseVODAO;

	public void setBaseVODAO(IBaseVODAO baseVODAO) {
		this.baseVODAO = baseVODAO;
	}
}
