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
package de.pellepelster.myadmin.client.gwt;

import com.google.gwt.core.client.EntryPoint;

import de.pellepelster.myadmin.client.gwt.modules.dictionary.editor.DictionaryEditorModuleUIFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.search.DictionarySearchModuleUIFactory;
import de.pellepelster.myadmin.client.gwt.modules.hierarchical.HierarchicalTreeModuleUIFactory;
import de.pellepelster.myadmin.client.gwt.modules.navigation.ModuleNavigationModuleUIFactory;
import de.pellepelster.myadmin.client.web.module.ModuleUIFactoryRegistry;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModule;
import de.pellepelster.myadmin.client.web.modules.hierarchical.HierarchicalTreeModule;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;

public class GwtClient implements EntryPoint
{

	/** {@inheritDoc} */
	@Override
	public void onModuleLoad()
	{
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(ModuleNavigationModule.class, new ModuleNavigationModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(DictionarySearchModule.class, new DictionarySearchModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(DictionaryEditorModule.class, new DictionaryEditorModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(HierarchicalTreeModule.class, new HierarchicalTreeModuleUIFactory());
	}

}
