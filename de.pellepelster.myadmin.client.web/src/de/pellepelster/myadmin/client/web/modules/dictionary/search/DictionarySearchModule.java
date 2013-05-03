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
package de.pellepelster.myadmin.client.web.modules.dictionary.search;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.layout.DictionarySearchInput;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.BaseDictionarySearchModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryModelProvider;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;

/**
 * Dictionary search module
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public class DictionarySearchModule extends BaseDictionarySearchModule
{
	private IDictionaryModel dictionaryModel;

	private final DictionarySearchInput input;

	public DictionarySearchModule(ModuleVO moduleVO, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{

		super(moduleVO, moduleCallback, parameters);

		input = new DictionarySearchInput(getSearchDictionaryName());

		DictionaryModelProvider.getDictionaryModel(getSearchDictionaryName(), new AsyncCallback<IDictionaryModel>()
		{

			/** {@inheritDoc} */
			@Override
			public void onFailure(Throwable caught)
			{
				getModuleCallback().onFailure(caught);
			}

			/** {@inheritDoc} */
			@Override
			public void onSuccess(IDictionaryModel dictionaryModel)
			{
				DictionarySearchModule.this.dictionaryModel = dictionaryModel;

				List<String> referencedDictionaryNames = DictionaryModelUtil.getReferencedDictionaryModels(dictionaryModel);

				DictionaryModelProvider.cacheDictionaryModels(referencedDictionaryNames, new AsyncCallback<List<IDictionaryModel>>()
				{

					/** {@inheritDoc} */
					@Override
					public void onFailure(Throwable caught)
					{
						GWT.log("error retrieving dictionary model", caught);
					}

					/** {@inheritDoc} */
					@Override
					public void onSuccess(List<IDictionaryModel> result)
					{
						getModuleCallback().onSuccess(DictionarySearchModule.this);
					}
				});

			}
		});
	}

	public IDictionaryModel getDictionaryModel()
	{
		return dictionaryModel;
	}

	public String getTitle()
	{
		return DictionaryUtil.getSearchTitle(dictionaryModel);
	}

}
