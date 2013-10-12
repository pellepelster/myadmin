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
package de.pellepelster.myadmin.demo.client.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.spring.GwtSpringTest;
import com.googlecode.gwt.test.spring.GwtTestContextLoader;

import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.MyAdminAsyncServiceLocator;
import de.pellepelster.myadmin.server.test.base.BaseJndiContextTest;

@ContextConfiguration(locations = { "classpath:DemoClientTestApplicationContext.xml", "classpath:MyAdminServerApplicationContext-gen.xml",
		"classpath:MyAdminServerApplicationContextServices-gen.xml", "classpath:MyAdminServerApplicationContext.xml",
		"classpath:DemoServerApplicationContext-gen.xml", "classpath:DemoServerApplicationContextServices-gen.xml",
		"classpath:MyAdminServerInvokerServices-gen.xml", "classpath:DemoDB-gen.xml" }, loader = GwtTestContextLoader.class)
@GwtModule("de.pellepelster.myadmin.demo.DemoTest")
public class DemoClientTest extends GwtSpringTest
{
	@Autowired
	private MyAdminAsyncServiceLocator myAdminAsyncServiceLocator;

	@BeforeClass
	public static void initJndi() throws Exception
	{
		BaseJndiContextTest.initJndi("Demo");
	}

	@Test
	public void test1()
	{
		MyAdmin.getInstance().setMyAdminGWTRemoteServiceLocator(this.myAdminAsyncServiceLocator);
	}

	public void setMyAdminServiceLocator(MyAdminAsyncServiceLocator myAdminAsyncServiceLocator)
	{
		this.myAdminAsyncServiceLocator = myAdminAsyncServiceLocator;
	}

}
