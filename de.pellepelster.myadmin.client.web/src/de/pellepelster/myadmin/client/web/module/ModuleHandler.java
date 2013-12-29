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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.module.BaseModule;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleDefinitionVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
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

	private int moduleCounter = 0;

	private static ModuleHandler instance;

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

	private void getModuleInstance(ModuleVO moduleVO, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		this.moduleCounter++;
		IModuleFactory factory = ModuleFactoryRegistry.getInstance().getModuleFactory(moduleVO.getModuleDefinition().getName());

		parameters.put(BaseModule.MODULE_COUNTER_PARAMETER_ID, this.moduleCounter);

		factory.getNewInstance(moduleVO, moduleCallback, parameters);
	}

	public void startModule(IModule module, Map<String, Object> parameters)
	{
		startModule(module, null, parameters);
	}

	public void startModule(IModule module, String location, Map<String, Object> parameters)
	{
		MyAdmin.getInstance().getLayoutFactory().startModuleUI(module, location, parameters);
	}

	public void startModule(final String moduleLocator, Map<String, Object> parameters)
	{
		startModule(moduleLocator, null, parameters);
	}

	public void startModule(final String moduleLocator)
	{
		startModule(moduleLocator, null, new HashMap<String, Object>());
	}

	public void startModule(final String moduleLocator, final Map<String, Object> parameters, final AsyncCallback<IModule> moduleAsyncCallback)
	{
		if (moduleLocator.contains("="))
		{
			Map<String, String> locatorSegments = Splitter.on("&").withKeyValueSeparator("=").split(moduleLocator);

			if (locatorSegments.containsKey(IModule.MODULE_NAME_PARAMETER_NAME))
			{
				startModuleByName(locatorSegments.get(IModule.MODULE_NAME_PARAMETER_NAME), parameters, moduleAsyncCallback);
			}
			else if (locatorSegments.containsKey(IModule.MODULE_ID_PARAMETER_NAME))
			{
				final String moduleId = locatorSegments.get(IModule.MODULE_ID_PARAMETER_NAME);

				ModuleVO moduleVO = new ModuleVO();
				moduleVO.setName(moduleLocator);
				moduleVO.getProperties().putAll(locatorSegments);

				ModuleDefinitionVO moduleDefinitionVO = new ModuleDefinitionVO();
				moduleDefinitionVO.setName(moduleId);

				moduleVO.setModuleDefinition(moduleDefinitionVO);

				getModuleInstance(moduleVO, moduleAsyncCallback, parameters);

			}
			else
			{
				throw new RuntimeException("unable to find module for locator '" + moduleLocator + "'");
			}

		}
		else
		{
			startModuleByName(moduleLocator, parameters, moduleAsyncCallback);
		}

	}

	private void startModuleByName(final String moduleName, final Map<String, Object> parameters, final AsyncCallback<IModule> moduleAsyncCallback)
	{
		final GenericFilterVO<ModuleVO> genericFilterVO = ClientGenericFilterBuilder.createGenericFilter(ModuleVO.class)
				.addCriteria(ModuleVO.FIELD_NAME, moduleName).getGenericFilter();
		genericFilterVO.addAssociation(ModuleVO.FIELD_PROPERTIES);

		MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(genericFilterVO, new BaseErrorAsyncCallback<List<ModuleVO>>()
		{
			/** {@inheritDoc} */
			@Override
			public void onSuccess(List<ModuleVO> result)
			{
				if (result.size() == 1)
				{
					getModuleInstance(result.get(0), moduleAsyncCallback, parameters);
				}
				else
				{
					ModuleVO moduleVO = new ModuleVO();
					moduleVO.setName(moduleName);

					ModuleDefinitionVO moduleDefinitionVO = new ModuleDefinitionVO();
					moduleDefinitionVO.setName(moduleName);

					moduleVO.setModuleDefinition(moduleDefinitionVO);

					getModuleInstance(moduleVO, moduleAsyncCallback, parameters);
				}
			}
		});
	}

	public void startModule(final String moduleName, final String location)
	{
		startModule(moduleName, location, new HashMap<String, Object>());
	}

	public void startModule(final String moduleName, final String location, final Map<String, Object> parameters)
	{
		startModule(moduleName, parameters, new AsyncCallback<IModule>()
		{

			/** {@inheritDoc} */
			@Override
			public void onFailure(Throwable caught)
			{
				throw new RuntimeException("error starting module '" + moduleName + "', reason was: ", caught);
			}

			/** {@inheritDoc} */
			@Override
			public void onSuccess(IModule module)
			{
				startModule(module, location, parameters);
			}
		});
	}
}
