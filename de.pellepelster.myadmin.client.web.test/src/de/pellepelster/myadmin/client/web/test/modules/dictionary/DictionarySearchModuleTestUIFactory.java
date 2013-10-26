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

import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.web.module.IModuleUIFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModule;

public class DictionarySearchModuleTestUIFactory implements IModuleUIFactory<Object, DictionarySearchModule<?>>
{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IModuleUI<Object, DictionarySearchModule<?>> getNewInstance(IModule module, IModuleUI<?, ?> previousModuleUI, Map<String, Object> parameters)
	{
		return new DictionarySearchModuleTestUI((DictionarySearchModule) module);
	}

}
