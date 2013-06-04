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

import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.validation.INamesAreUniqueValidationHelper;

import com.google.inject.Binder;

import de.pellepelster.myadmin.dsl.naming.MyAdminQualifiedNameProvider;
import de.pellepelster.myadmin.dsl.validation.MyAdminNamesAreUniqueValidationHelper;

/**
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 */
public class MyAdminDslRuntimeModule extends de.pellepelster.myadmin.dsl.AbstractMyAdminDslRuntimeModule {

	@Override
	public void configure(Binder binder) {
		super.configure(binder);
		binder.bind(INamesAreUniqueValidationHelper.class).to(MyAdminNamesAreUniqueValidationHelper.class);
	}

	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return MyAdminQualifiedNameProvider.class;
	}

}
