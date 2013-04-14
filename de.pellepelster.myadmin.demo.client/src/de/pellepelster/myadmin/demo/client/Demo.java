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
package de.pellepelster.myadmin.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;

import de.pellepelster.myadmin.client.base.modules.dictionary.hooks.IEditorSaveHook;
import de.pellepelster.myadmin.client.gwt.ControlHandler;
import de.pellepelster.myadmin.client.gwt.GWTLayoutFactory;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;
import de.pellepelster.myadmin.demo.client.web.dictionaries.CountryHookRegistration;

public class Demo implements EntryPoint
{
	/** {@inheritDoc} */
	@Override
	public void onModuleLoad()
	{
		CountryHookRegistration.setEditorSaveHook(new IEditorSaveHook()
		{

			@Override
			public void onSave(AsyncCallback<Void> asyncCallback)
			{
				asyncCallback.onSuccess(null);
			}
		});

		GWTLayoutFactory gwtLayoutFactory = new GWTLayoutFactory(Unit.PX);
		MyAdmin.getInstance().setLayoutFactory(gwtLayoutFactory);
		MyAdmin.getInstance().setControlHandler(new ControlHandler());

		gwtLayoutFactory.startModule(ModuleNavigationModule.MODULE_ID, Direction.WEST.toString());

	}
}
