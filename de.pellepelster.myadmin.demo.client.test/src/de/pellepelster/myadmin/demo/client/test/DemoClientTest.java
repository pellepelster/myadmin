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
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;

import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;

public class DemoClientTest extends GWTTestCase {

	public String getModuleName() {
		return "de.pellepelster.myadmin.demo.DemoTest";
	}

	@Test
	public void test1() {

		MyAdmin.getInstance().startModule(ModuleNavigationModule.MODULE_ID, Direction.WEST.toString());

	}

}
