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
package de.pellepelster.myadmin.dsl.scoping;

import org.eclipse.xtext.generator.scoping.AbstractScopingFragment;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.scoping.impl.ImportUriGlobalScopeProvider;
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider;

public class MyAdminDslScopingFragment extends AbstractScopingFragment
{

	@Override
	protected Class<? extends IGlobalScopeProvider> getGlobalScopeProvider()
	{
		return ImportUriGlobalScopeProvider.class;
	}

	@Override
	protected Class<? extends IScopeProvider> getLocalScopeProvider()
	{
		return ImportedNamespaceAwareLocalScopeProvider.class;
	}

}
