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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

import de.pellepelster.myadmin.client.base.layout.ILayoutFactory;
import de.pellepelster.myadmin.client.web.module.ModuleFactoryRegistry;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModuleFactory;
import de.pellepelster.myadmin.client.web.modules.hierarchical.HierarchicalTreeModuleFactory;
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
			init();
		}

		return instance;
	}

	public final static MyAdminMessages MESSAGES = ((MyAdminMessages) GWT.create(MyAdminMessages.class));;

	public final static MyAdminResources RESOURCES = ((MyAdminResources) GWT.create(MyAdminResources.class));;

	private IMyAdminGWTRemoteServiceLocator myAdminGWTRemoteServiceLocator;

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
	public static void init()
	{
		ModuleFactoryRegistry.getInstance().addModuleFactory(new DictionarySearchModuleFactory());
		ModuleFactoryRegistry.getInstance().addModuleFactory(new DictionaryEditorModuleFactory());
		ModuleFactoryRegistry.getInstance().addModuleFactory(new ModuleNavigationModuleFactory());
		ModuleFactoryRegistry.getInstance().addModuleFactory(new HierarchicalTreeModuleFactory());

		MyAdminClientConfiguration.registerAll();
	}

	public void setLayoutFactory(ILayoutFactory<?, ?> layoutFactory)
	{
		this.layoutFactory = layoutFactory;
	}

	public void setMyAdminGWTRemoteServiceLocator(IMyAdminGWTRemoteServiceLocator myAdminGWTRemoteServiceLocator)
	{
		this.myAdminGWTRemoteServiceLocator = myAdminGWTRemoteServiceLocator;
	}

}
