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
package de.pellepelster.myadmin.client.web.test.modules.dictionary;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.module.ModuleUtils;
import de.pellepelster.myadmin.client.web.module.BaseModuleUIFactory;
import de.pellepelster.myadmin.client.web.module.ModuleHandler;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModule;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

public class DictionaryEditorModuleTestUIFactory extends BaseModuleUIFactory<Object, DictionaryEditorModuleTestUI>
{

	public DictionaryEditorModuleTestUIFactory()
	{
		super(new String[] { DictionaryEditorModule.UI_MODULE_ID });
	}

	@Override
	public void getNewInstance(final String moduleUrl, final AsyncCallback<DictionaryEditorModuleTestUI> moduleCallback, Map<String, Object> parameters,
			final IModuleUI previousModuleUI)
	{
		ModuleHandler.getInstance().startModule(ModuleUtils.concatenate(moduleUrl, DictionaryEditorModule.MODULE_LOCATOR), parameters,
				new BaseErrorAsyncCallback<IModule>()
				{

					@Override
					public void onSuccess(IModule result)
					{
						if (supports(moduleUrl, DictionaryEditorModule.MODULE_ID))
						{
							moduleCallback.onSuccess(new DictionaryEditorModuleTestUI((DictionaryEditorModule) result));
						}
					}
				});
	}

}
