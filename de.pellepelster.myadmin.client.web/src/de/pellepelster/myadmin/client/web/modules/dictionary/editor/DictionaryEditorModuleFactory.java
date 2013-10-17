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
package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.module.IModuleFactory;
import de.pellepelster.myadmin.client.web.module.ModuleHandler;

public class DictionaryEditorModuleFactory implements IModuleFactory
{
	public static void openEditor(String dictionaryName, HashMap<String, Object> parameters)
	{
		openEditorInternal(dictionaryName, parameters);
	}

	public static void openEditor(String editorModelName)
	{
		openEditorInternal(editorModelName, new HashMap<String, Object>());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void openEditorInternal(String dictionaryName, final Map<String, Object> parameters)
	{

		new DictionaryEditorModule(dictionaryName, new AsyncCallback<IModule>()
		{

			/** {@inheritDoc} */
			@Override
			public void onFailure(Throwable caught)
			{
				throw new RuntimeException("error starting DictionaryEditorModule", caught);
			}

			/**
			 * @param result
			 */
			@Override
			public void onSuccess(IModule result)
			{
				ModuleHandler.getInstance().startModule(result, parameters);
			}
		}, parameters);

	}

	public static void openEditorForId(String dictionaryName, long id)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(DictionaryEditorModule.ID_PARAMETER_ID, id);

		openEditorInternal(dictionaryName, parameters);
	}

	/** {@inheritDoc} */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getNewInstance(ModuleVO moduleVO, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		new DictionaryEditorModule(moduleVO, moduleCallback, parameters);
	}

}
