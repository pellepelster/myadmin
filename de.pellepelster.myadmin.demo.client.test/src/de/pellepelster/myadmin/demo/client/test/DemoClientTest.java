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

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;

import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;
import de.pellepelster.myadmin.client.web.test.MyAdminTest;

public class DemoClientTest extends GWTTestCase
{

	@Override
	public String getModuleName()
	{
		return "de.pellepelster.myadmin.demo.DemoTest";
	}

	@Test
	public void testNavigationTree()
	{
		MyAdminTest.getInstance().startModule(ModuleNavigationModule.MODULE_ID, Direction.WEST.toString(), new AsyncCallback<IModuleUI>()
		{

			@Override
			public void onSuccess(IModuleUI result)
			{
				result.toString();
			}

			@Override
			public void onFailure(Throwable caught)
			{
				// TODO Auto-generated method stub

			}
		});

		delayTestFinish(2000);
	}

}
