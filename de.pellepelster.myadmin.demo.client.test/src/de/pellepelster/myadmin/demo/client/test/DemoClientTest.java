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

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;

import de.pellepelster.myadmin.client.gwt.test.MyAdminGwtTest;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.MyAdminAsyncServiceLocator;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.server.test.base.BaseJndiContextTest;
import de.pellepelster.myadmin.tools.SpringModelUtils;
import de.pellepelster.myadmin.tools.dictionary.DictionaryImportRunner;

@ContextConfiguration(locations = { "classpath:DemoClientTestApplicationContext.xml", "classpath:MyAdminServerApplicationContext-gen.xml",
		"classpath:MyAdminServerApplicationContextServices-gen.xml", "classpath:MyAdminServerApplicationContext.xml",
		"classpath:DemoServerApplicationContext-gen.xml", "classpath:DemoServerApplicationContextServices-gen.xml",
		"classpath:MyAdminServerInvokerServices-gen.xml", "classpath:DemoDB-gen.xml" })
public class DemoClientTest extends BaseJndiContextTest
{

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private ApplicationEventMulticaster applicationEventMulticaster;

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
		Resource modelResource = SpringModelUtils.getResource("classpath:Demo.msl");
		List<Resource> modelResources = SpringModelUtils.getResources("classpath*:model/*.msl");

		DictionaryImportRunner dictionaryImportRunner = new DictionaryImportRunner(this.baseEntityService, this.applicationEventMulticaster, modelResources,
				modelResource);
		dictionaryImportRunner.run();

		MyAdminGwtTest myAdminGwtTest = new MyAdminGwtTest(this.myAdminAsyncServiceLocator);
		MyAdmin.getInstance().setMyAdminGWTRemoteServiceLocator(this.myAdminAsyncServiceLocator);
	}

	public void setMyAdminServiceLocator(MyAdminAsyncServiceLocator myAdminAsyncServiceLocator)
	{
		this.myAdminAsyncServiceLocator = myAdminAsyncServiceLocator;
	}

}
