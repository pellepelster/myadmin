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
package de.pellepelster.myadmin.client.base.module;

/**
 * MyAdmin module interface
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface IModule
{

	String getModuleId();

	/**
	 * Returns the module name
	 * 
	 * @return
	 */
	String getModuleName();

	/**
	 * False if the module can be started multiple times
	 * 
	 * @return
	 */
	boolean isSingleton();
}
