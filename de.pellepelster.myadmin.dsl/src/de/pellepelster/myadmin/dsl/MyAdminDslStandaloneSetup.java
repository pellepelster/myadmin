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
package de.pellepelster.myadmin.dsl;

/**
 * Initialization support for running Xtext languages without equinox extension
 * registry
 */
public class MyAdminDslStandaloneSetup extends MyAdminDslStandaloneSetupGenerated
{

	public static void doSetup()
	{
		new MyAdminDslStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}
