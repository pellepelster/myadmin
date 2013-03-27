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
package de.pellepelster.myadmin.client.web.module;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;

/**
 * Describes a factory for module instantiation
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public interface IModuleFactory
{

	/**
	 * Creates a module instance
	 * 
	 * @param moduleVO
	 * @param moduleCallback
	 * @param parameters
	 */
	void getNewInstance(ModuleVO moduleVO, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters);

}
