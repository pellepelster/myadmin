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
package de.pellepelster.myadmin.client.web.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.module.BaseModule;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

/**
 * Handler for module loading
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public final class ModuleHandler
{

	public static EventBus MODULE_EVENT_BUS = GWT.create(SimpleEventBus.class);

	private int moduleCounter = 0;

	private static ModuleHandler instance;

	private List<IModule> moduleInstances = new ArrayList<IModule>();

	public static ModuleHandler getInstance()
	{
		if (instance == null)
		{
			instance = new ModuleHandler();
		}

		return instance;
	}

	private ModuleHandler()
	{
	}

	public void startUIModule(final String moduleUrl, String location)
	{
	}

	public void startUIModule(final String moduleUrl)
	{
	}

	public void startUIModule(final String moduleUrl, Map<String, Object> parameters)
	{
	}

	public void startUIModule(final String moduleUrl, final AsyncCallback<IModuleUI> moduleCallback, Map<String, Object> parameters,
			IModuleUI<?, ?> previousModuleUI)
	{

		if (ModuleUIFactoryRegistry.getInstance().supports(moduleUrl))
		{
			ModuleUIFactoryRegistry.getInstance().getModuleFactory(moduleUrl).getNewInstance(moduleUrl, new BaseErrorAsyncCallback<IModuleUI>()
			{

				@Override
				public void onSuccess(IModuleUI moduleUI)
				{
					MyAdmin.getInstance().getLayoutFactory().showModuleUI(moduleUI);
					moduleCallback.onSuccess(moduleUI);
				}
			}, parameters, previousModuleUI);
		}
		else
		{
			moduleCallback.onFailure(new RuntimeException("unsupported module url '" + moduleUrl + "'"));
		}

	}

	public void startModule(final String moduleUrl, Map<String, Object> parameters, final AsyncCallback<IModule> moduleCallback)
	{

		if (ModuleFactoryRegistry.getInstance().supports(moduleUrl))
		{
			this.moduleCounter++;
			parameters.put(BaseModule.MODULE_COUNTER_PARAMETER_ID, this.moduleCounter);

			ModuleFactoryRegistry.getInstance().getModuleFactory(moduleUrl).getNewInstance(moduleUrl, moduleCallback, parameters);
		}
		else
		{
			moduleCallback.onFailure(new RuntimeException("unsupported module url '" + moduleUrl + "'"));
		}
	}

	// private void startModuleUI(IModule module, String location, Map<String,
	// Object> parameters)
	// {
	// MyAdmin.getInstance().getLayoutFactory().startModuleUI(module, location,
	// parameters);
	// }
	//
	// public void startModuleUI(IModule module, Map<String, Object> parameters)
	// {
	// MyAdmin.getInstance().getLayoutFactory().startModuleUI(module, null,
	// parameters);
	// }
	//
	// public void startModule(final String moduleId, Map<String, Object>
	// parameters)
	// {
	// startModule(moduleId, null, parameters);
	// }
	//
	// public void startModule(final String moduleId)
	// {
	// startModule(moduleId, null, new HashMap<String, Object>());
	// }
	//
	// public void startModule(final String moduleUrl, final String location)
	// {
	// startModule(moduleUrl, location, new HashMap<String, Object>());
	// }
	//
	// public void startModule(final String moduleUrl, final String location,
	// final Map<String, Object> parameters)
	// {
	// getModuleInstance(moduleUrl, parameters, new AsyncCallback<IModule>()
	// {
	//
	// /** {@inheritDoc} */
	// @Override
	// public void onFailure(Throwable caught)
	// {
	// throw new RuntimeException("error starting module '" + moduleUrl +
	// "', reason was: ", caught);
	// }
	//
	// /** {@inheritDoc} */
	// @Override
	// public void onSuccess(IModule module)
	// {
	// ModuleHandler.this.moduleInstances.add(module);
	// startModuleUI(module, location, parameters);
	// }
	// });
	// }
}
