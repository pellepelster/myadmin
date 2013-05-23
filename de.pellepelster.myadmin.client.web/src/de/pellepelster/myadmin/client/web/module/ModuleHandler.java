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

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.jpql.GenericFilterFactory;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleDefinitionVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;

/**
 * Handler for module loading
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public final class ModuleHandler
{

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

		IModuleFactory factory = ModuleFactoryRegistry.getInstance().getModuleFactory(moduleVO.getModuleDefinition().getName());
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

	public void startModule(final String moduleName)
	{
		startModule(moduleName, null, new HashMap<String, Object>());
	}

	public void startModule(final String moduleName, final Map<String, Object> parameters, final AsyncCallback<IModule> moduleAsyncCallback)
	{

		GenericFilterVO<ModuleVO> genericFilterVO = GenericFilterFactory.createGenericFilter(ModuleVO.class, ModuleVO.FIELD_NAME, moduleName);
		genericFilterVO.addAssociation(ModuleVO.FIELD_PROPERTIES);

		MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(genericFilterVO, new AsyncCallback<List<ModuleVO>>()
		{

			/** {@inheritDoc} */
			@Override
			public void onFailure(Throwable caught)
			{
				moduleAsyncCallback.onFailure(caught);
			}

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
					// no module found, we try to use the module definition as
					// module

					GenericFilterVO<ModuleDefinitionVO> genericFilterVO = GenericFilterFactory.createGenericFilter(ModuleDefinitionVO.class,
							ModuleDefinitionVO.FIELD_NAME, moduleName);

					MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService()
							.filter(genericFilterVO, new AsyncCallback<List<ModuleDefinitionVO>>()
							{

								/** {@inheritDoc} */
								@Override
								public void onFailure(Throwable caught)
								{
									moduleAsyncCallback.onFailure(caught);
								}

								@Override
								public void onSuccess(List<ModuleDefinitionVO> result)
								{
									if (result.size() == 1)
									{
										ModuleVO moduleVO = new ModuleVO();
										moduleVO.setName(moduleName);
										moduleVO.setModuleDefinition(result.get(0));

										getModuleInstance(moduleVO, moduleAsyncCallback, parameters);
									}
									else
									{
										moduleAsyncCallback.onFailure(new RuntimeException("error loading module by module name, module name was '"
												+ moduleName + "'"));
									}
								}
							});
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
