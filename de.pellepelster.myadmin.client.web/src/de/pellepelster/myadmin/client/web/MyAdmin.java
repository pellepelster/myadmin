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
package de.pellepelster.myadmin.client.web;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

import de.pellepelster.myadmin.client.base.layout.ILayoutFactory;
import de.pellepelster.myadmin.client.web.module.ModuleFactoryRegistry;
import de.pellepelster.myadmin.client.web.module.ModuleHandler;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModuleFactory;
import de.pellepelster.myadmin.client.web.modules.hierarchical.HierarchicalTreeModule;
import de.pellepelster.myadmin.client.web.modules.hierarchical.HierarchicalTreeModuleFactory;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModuleFactory;

/**
 * TODO pelle insert type comment
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public final class MyAdmin implements EntryPoint
{

	private ILayoutFactory<?, ?> layoutFactory;

	/** Shared instance of {@link MyAdmin} */
	private static MyAdmin instance;

	private static final Logger LOGGER = Logger.getLogger(MyAdmin.class.getName());

	public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);

	/**
	 * Returns the instance
	 * 
	 * @return
	 */
	public static MyAdmin getInstance()
	{
		if (instance == null)
		{
			instance = new MyAdmin();
		}

		return instance;
	}

	public final static MyAdminMessages MESSAGES = ((MyAdminMessages) GWT.create(MyAdminMessages.class));;

	public final static MyAdminResources RESOURCES = ((MyAdminResources) GWT.create(MyAdminResources.class));;

	private IMyAdminGWTRemoteServiceLocator myAdminGWTRemoteServiceLocator;

	private MyAdmin()
	{
		init();
	}

	public ILayoutFactory<?, ?> getLayoutFactory()
	{
		return this.layoutFactory;
	}

	public IMyAdminGWTRemoteServiceLocator getRemoteServiceLocator()
	{

		if (this.myAdminGWTRemoteServiceLocator != null)
		{
			return this.myAdminGWTRemoteServiceLocator;
		}

		return MyAdminGWTRemoteServiceLocator.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public void onModuleLoad()
	{
		instance = this;
		init();
	}

	/**
	 * Registers all MyAdmin modules
	 */
	public void init()
	{
		ModuleFactoryRegistry.getInstance().addModuleFactory(DictionarySearchModule.MODULE_ID, new DictionarySearchModuleFactory());
		ModuleFactoryRegistry.getInstance().addModuleFactory(DictionaryEditorModule.MODULE_ID, new DictionaryEditorModuleFactory());
		ModuleFactoryRegistry.getInstance().addModuleFactory(ModuleNavigationModule.MODULE_ID, new ModuleNavigationModuleFactory());
		ModuleFactoryRegistry.getInstance().addModuleFactory(HierarchicalTreeModule.MODULE_ID, new HierarchicalTreeModuleFactory());
	}

	public void setLayoutFactory(ILayoutFactory<?, ?> layoutFactory)
	{
		this.layoutFactory = layoutFactory;
	}

	public void setMyAdminGWTRemoteServiceLocator(IMyAdminGWTRemoteServiceLocator myAdminGWTRemoteServiceLocator)
	{
		this.myAdminGWTRemoteServiceLocator = myAdminGWTRemoteServiceLocator;
	}

	public void startModule(String moduleName, String location)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();

		startModule(moduleName, location, parameters);
	}

	public void startModule(String moduleName, String location, Map<String, Object> parameters)
	{
		ModuleHandler.getInstance().startModule(moduleName, location, parameters);
	}

	public void startModule(String moduleName, String location, Map<String, Object> parameters, String d)
	{
		ModuleHandler.getInstance().startModule(moduleName, location, parameters);
	}

}
