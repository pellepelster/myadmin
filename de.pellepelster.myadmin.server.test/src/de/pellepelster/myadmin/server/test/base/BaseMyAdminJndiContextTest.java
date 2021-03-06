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

import org.junit.BeforeClass;

public abstract class BaseMyAdminJndiContextTest extends BaseJndiContextTest {

	@BeforeClass
	public static void initJndi() throws Exception {
		initJndi("MyAdmin");
	}
}
