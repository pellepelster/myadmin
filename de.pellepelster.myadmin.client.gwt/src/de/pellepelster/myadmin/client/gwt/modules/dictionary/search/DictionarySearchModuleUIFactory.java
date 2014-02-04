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
import de.pellepelster.myadmin.client.web.module.IModuleUIFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModule;

public class DictionarySearchModuleUIFactory<VOType extends IBaseVO> implements IModuleUIFactory<Panel, DictionarySearchModule<VOType>>
{

	@Override
	public IModuleUI<Panel, DictionarySearchModule<VOType>> getNewInstance(DictionarySearchModule<VOType> module, IModuleUI<?, ?> previousModuleUI,
			Map<String, Object> parameters)
	{
		if (parameters.containsKey(DictionarySearchModule.SHOW_QUERY_SEARCH_PARAMETER_ID))
		{
			return new DictionarySearchQueryModuleUI<VOType>((DictionarySearchModule) module);

		}
		else if (parameters.containsKey(DictionarySearchModule.QUERY_TEXT_PARAMETER_ID))
		{
			return new DictionarySearchResultModuleUI<VOType>((DictionarySearchModule) module);

		}

		else
		{
			return new DictionarySearchModuleUI((DictionarySearchModule) module);
		}
	}
}
