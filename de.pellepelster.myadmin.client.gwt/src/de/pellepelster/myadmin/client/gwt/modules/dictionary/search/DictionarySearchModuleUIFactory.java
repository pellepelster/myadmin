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
package de.pellepelster.myadmin.client.gwt.modules.dictionary.search;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.gwt.modules.IGwtModuleUI;
import de.pellepelster.myadmin.client.web.module.BaseModuleUIFactory;
import de.pellepelster.myadmin.client.web.module.ModuleHandler;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModule;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

public class DictionarySearchModuleUIFactory<VOType extends IBaseVO> extends BaseModuleUIFactory<Panel, IGwtModuleUI>
{
	public DictionarySearchModuleUIFactory()
	{
		super(new String[] { DictionarySearchModule.SEARCH_QUERY_RESULT_UI_MODULE_ID, DictionarySearchModule.SEARCH_QUERY_UI_MODULE_ID,
				DictionarySearchModule.SEARCH_UI_MODULE_ID });
	}

	@Override
	public void getNewInstance(final String moduleUrl, final AsyncCallback<IGwtModuleUI> moduleCallback, Map<String, Object> parameters,
			IModuleUI previousModuleUI)
	{
		ModuleHandler.getInstance().startModule(DictionarySearchModule.MODULE_LOCATOR, parameters, new BaseErrorAsyncCallback<IModule>()
		{

			@Override
			public void onSuccess(IModule result)
			{

				if (supports(moduleUrl, DictionarySearchModule.SEARCH_QUERY_UI_MODULE_ID))
				{
					moduleCallback.onSuccess(new DictionarySearchQueryModuleUI((DictionarySearchModule) result));
				}
				else if (supports(moduleUrl, DictionarySearchModule.SEARCH_QUERY_RESULT_UI_MODULE_ID))
				{
					moduleCallback.onSuccess(new DictionarySearchResultModuleUI((DictionarySearchModule) result));
				}
				else if (supports(moduleUrl, DictionarySearchModule.SEARCH_UI_MODULE_ID))
				{
					moduleCallback.onSuccess(new DictionarySearchModuleUI((DictionarySearchModule) result));
				}
			}
		});
	}
}
