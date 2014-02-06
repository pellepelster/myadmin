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

import com.google.gwt.user.client.ui.Panel;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.web.module.BaseModuleUIFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModule;

public class DictionarySearchModuleUIFactory<VOType extends IBaseVO> extends BaseModuleUIFactory<Panel, DictionarySearchModule<VOType>>
{

	@Override
	public IModuleUI<Panel, DictionarySearchModule<VOType>> getNewInstance(DictionarySearchModule<VOType> module, IModuleUI<?, ?> previousModuleUI,
			Map<String, Object> parameters)
	{
		if (supports(module.getModuleUrl(), DictionarySearchQueryModuleUI.MODULE_ID))
		{
			return new DictionarySearchQueryModuleUI<VOType>((DictionarySearchModule) module);

		}
		else if (supports(module.getModuleUrl(), DictionarySearchResultModuleUI.MODULE_ID))
		{
			return new DictionarySearchResultModuleUI<VOType>((DictionarySearchModule) module);

		}

		else if (supports(module.getModuleUrl(), DictionarySearchModuleUI.MODULE_ID))
		{
			return new DictionarySearchModuleUI((DictionarySearchModule) module);
		}

		return null;
	}
}
