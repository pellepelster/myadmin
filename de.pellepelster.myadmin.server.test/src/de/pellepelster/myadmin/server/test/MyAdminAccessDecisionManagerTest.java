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

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.server.user.service.MyAdminAccessDecisionManager;

public class MyAdminAccessDecisionManagerTest extends AbstractMyAdminTest
{

	@Autowired
	private MyAdminAccessDecisionManager myAdminAccessDecisionManager;

	public MyAdminAccessDecisionManager getMyAdminAccessDecisionManager()
	{
		return myAdminAccessDecisionManager;
	}

	public void setMyAdminAccessDecisionManager(MyAdminAccessDecisionManager myAdminAccessDecisionManager)
	{
		this.myAdminAccessDecisionManager = myAdminAccessDecisionManager;
	}

	@Test
	public void testAuth()
	{
		myAdminAccessDecisionManager.toString();
	}

}
