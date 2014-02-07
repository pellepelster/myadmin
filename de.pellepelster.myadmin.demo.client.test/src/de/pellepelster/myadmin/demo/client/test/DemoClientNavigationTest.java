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

import org.junit.Ignore;
import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;

import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;
import de.pellepelster.myadmin.client.web.test.MyAdminTest;
import de.pellepelster.myadmin.client.web.test.modules.navigation.NavigationModuleTestUI;
import de.pellepelster.myadmin.client.web.test.modules.navigation.NavigationTreeTestElement;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

public class DemoClientNavigationTest extends GWTTestCase
{

	@Override
	public String getModuleName()
	{
		return "de.pellepelster.myadmin.demo.DemoTest";
	}

	private class NavigationTreeElementTest extends BaseErrorAsyncCallback<List<NavigationTreeTestElement>>
	{
		@Override
		public void onSuccess(List<NavigationTreeTestElement> result)
		{
			// TODO reactivate tests
			assertFalse(result.isEmpty());
			// result.assertChildNavigationText(0, "Masterdata");
			// result.assertChildNavigationText(1, "Test");
			finishTest();
		}
	}

	private class NavigationModuleTest extends BaseErrorAsyncCallback<NavigationModuleTestUI>
	{
		@Override
		public void onSuccess(NavigationModuleTestUI result)
		{
			result.getRootElements(new NavigationTreeElementTest());
		}
	}

	@Test
	@Ignore
	public void testNavigationTree()
	{
		delayTestFinish(10000);

		MyAdminTest.getInstance().startModule(ModuleNavigationModule.NAVIGATION_UI_MODULE_LOCATOR, NavigationModuleTestUI.class, Direction.WEST.toString(),
				new NavigationModuleTest());

	}

}
