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

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.module.ModuleUtils;
import de.pellepelster.myadmin.client.web.module.BaseModuleFactory;
import de.pellepelster.myadmin.client.web.module.ModuleHandler;

public class DictionaryEditorModuleFactory extends BaseModuleFactory
{

	@Override
	public boolean supports(String moduleUrl)
	{
		return ModuleUtils.urlContainsModuleId(moduleUrl, DictionaryEditorModule.MODULE_ID);
	}

	public static void openEditorForId(String dictionaryName, long voId)
	{
		ModuleHandler.getInstance().startUIModule(DictionaryEditorModule.getModuleUrlForDictionary(dictionaryName, voId));
	}

	public static void openEditor(String dictionaryName)
	{
		ModuleHandler.getInstance().startUIModule(DictionaryEditorModule.getModuleUrlForDictionary(dictionaryName));
	}

	public static void openEditor(String dictionaryName, Map<String, Object> parameters)
	{
		ModuleHandler.getInstance().startUIModule(DictionaryEditorModule.getModuleUrlForDictionary(dictionaryName), parameters);
	}

	/** {@inheritDoc} */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getNewInstance(String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		new DictionaryEditorModule(moduleUrl, moduleCallback, parameters);
	}

}
