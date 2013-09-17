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

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

import de.pellepelster.myadmin.client.base.layout.ILayoutFactory;
import de.pellepelster.myadmin.client.web.module.ModuleFactoryRegistry;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IControlFactory;
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
public final class MyAdmin implements EntryPoint {

	private ILayoutFactory<?, ?> layoutFactory;

	/** Shared instance of {@link MyAdmin} */
	private static MyAdmin instance;

	private static final Logger LOGGER = Logger.getLogger("MyAdmin");

	public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);

	/**
	 * Returns the instance
	 * 
	 * @return
	 */
	public static MyAdmin getInstance() {
		return instance;
	}

	/** Root URL for all MyAdmin services */
	private String rootUrl = "";

	public final static MyAdminMessages MESSAGES = ((MyAdminMessages) GWT.create(MyAdminMessages.class));;

	public final static MyAdminResources RESOURCES = ((MyAdminResources) GWT.create(MyAdminResources.class));;

	private IMyAdminGWTRemoteServiceLocator myAdminGWTRemoteServiceLocator;

	private IControlFactory controlHandler;

	/**
	 * Constructor for {@link MyAdmin}
	 */
	private MyAdmin() {
	}

	public IControlFactory getControlHandler() {
		return controlHandler;
	}

	public ILayoutFactory<?, ?> getLayoutFactory() {
		return layoutFactory;
	}

	public IMyAdminGWTRemoteServiceLocator getRemoteServiceLocator() {

		if (myAdminGWTRemoteServiceLocator != null) {
			return myAdminGWTRemoteServiceLocator;
		}

		return MyAdminGWTRemoteServiceLocator.getInstance();
	}

	/**
	 * Returns the root URL for MyAdmin services
	 * 
	 * @param rootUrl
	 */
	public String getRootUrl() {
		return rootUrl;
	}

	/** {@inheritDoc} */
	@Override
	public void onModuleLoad() {
		LOGGER.log(Level.INFO, "MyAdmin module started");
		instance = this;
		registerModules();
	}

	/**
	 * Registers all MyAdmin modules
	 */
	private void registerModules() {
		ModuleFactoryRegistry.getInstance().addModuleFactory(DictionarySearchModule.MODULE_ID, new DictionarySearchModuleFactory());
		ModuleFactoryRegistry.getInstance().addModuleFactory(DictionaryEditorModule.MODULE_ID, new DictionaryEditorModuleFactory());
		ModuleFactoryRegistry.getInstance().addModuleFactory(ModuleNavigationModule.MODULE_ID, new ModuleNavigationModuleFactory());
		ModuleFactoryRegistry.getInstance().addModuleFactory(HierarchicalTreeModule.MODULE_ID, new HierarchicalTreeModuleFactory());
	}

	public void setControlHandler(IControlFactory controlHandler) {
		this.controlHandler = controlHandler;
	}

	public void setLayoutFactory(ILayoutFactory<?, ?> layoutFactory) {
		this.layoutFactory = layoutFactory;
	}

	public void setMyAdminGWTRemoteServiceLocator(IMyAdminGWTRemoteServiceLocator myAdminGWTRemoteServiceLocator) {
		this.myAdminGWTRemoteServiceLocator = myAdminGWTRemoteServiceLocator;
	}

	/**
	 * Sets the root URL for MyAdmin services
	 * 
	 * @param rootUrl
	 */
	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

}
