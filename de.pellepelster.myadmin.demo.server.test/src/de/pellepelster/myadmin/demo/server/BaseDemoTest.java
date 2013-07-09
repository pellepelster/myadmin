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

import org.junit.BeforeClass;
import org.springframework.test.context.ContextConfiguration;

import de.pellepelster.myadmin.server.test.base.BaseJndiContextTest;

@ContextConfiguration(locations = { "classpath:/MyAdminDemoTestApplicationContext.xml", "classpath:/DemoServerApplicationContext-gen.xml",
		"classpath:/DemoServerApplicationContextServices-gen.xml", "classpath:/DemoDB-gen.xml" })
public abstract class BaseDemoTest extends BaseJndiContextTest
{

	@BeforeClass
	public static void initJndi() throws Exception
	{
		initJndi("Demo");
	}

}
