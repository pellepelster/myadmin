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

import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;
import de.pellepelster.myadmin.client.web.test.MyAdminTest;
import de.pellepelster.myadmin.client.web.test.modules.navigation.NavigationModuleTestUI;

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
		MyAdminTest.getInstance().startModule(ModuleNavigationModule.MODULE_ID, NavigationModuleTestUI.class, Direction.WEST.toString(),
				new AsyncCallback<NavigationModuleTestUI>()
				{

					@Override
					public void onSuccess(NavigationModuleTestUI result)
					{
						result.get();
					}

					@Override
					public void onFailure(Throwable caught)
					{
						throw new RuntimeException(caught);
					}
				});

		delayTestFinish(2000);
	}

}
