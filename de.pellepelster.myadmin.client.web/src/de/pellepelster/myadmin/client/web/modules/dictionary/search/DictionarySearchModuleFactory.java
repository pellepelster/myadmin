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

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.module.ModuleUtils;
import de.pellepelster.myadmin.client.web.module.BaseModuleFactory;
import de.pellepelster.myadmin.client.web.module.ModuleHandler;

public class DictionarySearchModuleFactory extends BaseModuleFactory
{
	public static void openSearch(String dictionaryName)
	{
		ModuleHandler.getInstance().startUIModule(DictionarySearchModule.geSearchModuleLocator(dictionaryName));
	}

	/** {@inheritDoc} */
	@Override
	public void getNewInstance(String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		new DictionarySearchModule(moduleUrl, moduleCallback, parameters);
	}

	@Override
	public boolean supports(String moduleUrl)
	{
		return ModuleUtils.urlContainsModuleId(moduleUrl, DictionarySearchModule.MODULE_ID);
	}

}
