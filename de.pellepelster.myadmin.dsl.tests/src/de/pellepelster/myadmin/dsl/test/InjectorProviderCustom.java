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
package de.pellepelster.myadmin.dsl.test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.pellepelster.myadmin.dsl.MyAdminDslInjectorProvider;
import de.pellepelster.myadmin.dsl.MyAdminDslRuntimeModule;
import de.pellepelster.myadmin.dsl.MyAdminDslStandaloneSetup;

public class InjectorProviderCustom extends MyAdminDslInjectorProvider
{

	private final Injector injector = new MyAdminDslStandaloneSetup()
	{
		@Override
		public Injector createInjector()
		{
			return Guice.createInjector(new MyAdminDslRuntimeModule()
			{
				@Override
				public ClassLoader bindClassLoaderToInstance()
				{
					return InjectorProviderCustom.class.getClassLoader();
				}
			});
		}
	}.createInjectorAndDoEMFRegistration();;

	@Override
	public Injector getInjector()
	{
		return injector;
	}
}
