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
package de.pellepelster.myadmin.client.web.modules.hierarchical;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.modules.dictionary.hooks.ClientHookRegistry;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.modules.BaseModuleHierarchicalTreeModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryModelProvider;
import de.pellepelster.myadmin.client.web.services.IHierachicalServiceGWTAsync;

public class HierarchicalTreeModule extends BaseModuleHierarchicalTreeModule
{
	private HierarchicalConfigurationVO hierarchicalConfiguration;

	public HierarchicalTreeModule(ModuleVO moduleVO, final AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(moduleVO, moduleCallback, parameters);

		final IHierachicalServiceGWTAsync hierachicalService = MyAdmin.getInstance().getRemoteServiceLocator().getHierachicalService();

		hierachicalService.getConfigurationById(getHierarchicalTreeId(), new AsyncCallback<HierarchicalConfigurationVO>()
		{

			/** {@inheritDoc} */
			@Override
			public void onFailure(Throwable caught)
			{
				moduleCallback.onFailure(caught);
			}

			@Override
			public void onSuccess(HierarchicalConfigurationVO result)
			{
				HierarchicalTreeModule.this.hierarchicalConfiguration = result;

				Set<String> dictionaryNames = new HashSet<String>();
				for (Map.Entry<String, List<String>> entry : HierarchicalTreeModule.this.hierarchicalConfiguration.getDictionaryHierarchy().entrySet())
				{
					dictionaryNames.add(entry.getKey());

					for (String dictionaryName : entry.getValue())
					{
						if (dictionaryName != null)
						{
							dictionaryNames.addAll(entry.getValue());
						}
					}
				}

				DictionaryModelProvider.cacheDictionaryModels(new ArrayList<String>(dictionaryNames), new AsyncCallback<List<IDictionaryModel>>()
				{
					@Override
					public void onFailure(Throwable caught)
					{
						moduleCallback.onFailure(caught);
					}

					@Override
					public void onSuccess(List<IDictionaryModel> result)
					{
						for (String dictionaryId : HierarchicalTreeModule.this.hierarchicalConfiguration.getDictionaryIds())
						{
							List<String> childDictionaryIds = HierarchicalTreeModule.this.hierarchicalConfiguration.getChildDictionaryIds(dictionaryId);

							if (!childDictionaryIds.isEmpty())
							{
								List<IDictionaryModel> childDictionaries = DictionaryModelProvider.getCachedDictionaryModels(childDictionaryIds);
								ClientHookRegistry.getInstance().addEditorHook(dictionaryId, new HierarchicalEditorHook<IBaseVO>(childDictionaries));
							}
						}

						getModuleCallback().onSuccess(HierarchicalTreeModule.this);
					}
				});
			}
		});

	}

	public HierarchicalConfigurationVO getHierarchicalConfiguration()
	{
		return this.hierarchicalConfiguration;
	}

	@Override
	public String getModuleId()
	{
		return this.hierarchicalConfiguration.getId();
	}

}
