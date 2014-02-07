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
package de.pellepelster.myadmin.client.gwt.modules.hierarchical;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;

import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.module.ModuleUtils;
import de.pellepelster.myadmin.client.web.module.BaseModuleUIFactory;
import de.pellepelster.myadmin.client.web.module.ModuleHandler;
import de.pellepelster.myadmin.client.web.modules.hierarchical.HierarchicalTreeModule;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

public class HierarchicalTreeModuleUIFactory extends BaseModuleUIFactory<Panel, HierarchicalTreeModuleUI>
{
	public HierarchicalTreeModuleUIFactory()
	{
		super(new String[] { HierarchicalTreeModule.UI_MODULE_ID });
	}

	@Override
	public void getNewInstance(final String moduleUrl, final AsyncCallback<HierarchicalTreeModuleUI> moduleCallback, Map<String, Object> parameters,
			IModuleUI previousModuleUI)
	{
		ModuleHandler.getInstance().startModule(HierarchicalTreeModule.MODULE_LOCATOR, ModuleUtils.getUrlParameters(moduleUrl),
				new BaseErrorAsyncCallback<IModule>()
				{
					@Override
					public void onSuccess(IModule result)
					{
						moduleCallback.onSuccess(new HierarchicalTreeModuleUI((HierarchicalTreeModule) result));
					}
				});
	}

}
