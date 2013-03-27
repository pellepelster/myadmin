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
package de.pellepelster.myadmin.client.gwt.test;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;

import de.pellepelster.myadmin.client.gwt.ControlHandler;
import de.pellepelster.myadmin.client.gwt.GWTLayoutFactory;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.hierarchical.HierarchicalTreeModule;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;
import de.pellepelster.myadmin.client.web.test.TestHierarchicalServiceGWTAsync;
import de.pellepelster.myadmin.client.web.test.TestMyAdminRemoteServiceLocator;

/**
 * myadmin gwt test application
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class MyAdminGwtTest implements EntryPoint
{

	/** {@inheritDoc} */
	@SuppressWarnings("rawtypes")
	@Override
	public void onModuleLoad()
	{

		GWTLayoutFactory gwtLayoutFactory = new GWTLayoutFactory(Unit.PCT);

		MyAdmin.getInstance().setLayoutFactory(gwtLayoutFactory);
		MyAdmin.getInstance().setMyAdminGWTRemoteServiceLocator(new TestMyAdminRemoteServiceLocator());
		MyAdmin.getInstance().setControlHandler(new ControlHandler());

		gwtLayoutFactory.startModule(ModuleNavigationModule.MODULE_ID, Direction.WEST.toString());
		gwtLayoutFactory.startModule(HierarchicalTreeModule.MODULE_ID, Direction.WEST.toString(),
				HierarchicalTreeModule.getParameterMap(TestHierarchicalServiceGWTAsync.HIERARCHICAL_TREE1));
	}
}