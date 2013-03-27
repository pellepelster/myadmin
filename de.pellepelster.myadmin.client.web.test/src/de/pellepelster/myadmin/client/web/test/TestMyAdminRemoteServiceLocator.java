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
package de.pellepelster.myadmin.client.web.test;

import de.pellepelster.myadmin.client.web.IMyAdminGWTRemoteServiceLocator;
import de.pellepelster.myadmin.client.web.services.IBaseEntityServiceGWTAsync;
import de.pellepelster.myadmin.client.web.services.IDictionaryServiceGWTAsync;
import de.pellepelster.myadmin.client.web.services.IHierachicalServiceGWTAsync;
import de.pellepelster.myadmin.client.web.services.IModuleServiceGWTAsync;
import de.pellepelster.myadmin.client.web.services.IUserServiceGWTAsync;

public class TestMyAdminRemoteServiceLocator implements IMyAdminGWTRemoteServiceLocator
{

	private final IBaseEntityServiceGWTAsync baseEntityServiceGWTAsync;
	private final IDictionaryServiceGWTAsync dictionaryServiceGWTAsync;
	private final IHierachicalServiceGWTAsync hierachicalServiceGWTAsync;

	public TestMyAdminRemoteServiceLocator()
	{
		super();
		baseEntityServiceGWTAsync = new TestBaseEntityServiceGWTAsync();
		dictionaryServiceGWTAsync = new TestDictionaryServiceGWTAsync();
		hierachicalServiceGWTAsync = new TestHierarchicalServiceGWTAsync();
	}

	/** {@inheritDoc} */
	@Override
	public IBaseEntityServiceGWTAsync getBaseEntityService()
	{
		return baseEntityServiceGWTAsync;
	}

	/** {@inheritDoc} */
	@Override
	public IDictionaryServiceGWTAsync getDictionaryService()
	{
		return dictionaryServiceGWTAsync;
	}

	/** {@inheritDoc} */
	@Override
	public IHierachicalServiceGWTAsync getHierachicalService()
	{
		return hierachicalServiceGWTAsync;
	}

	/** {@inheritDoc} */
	@Override
	public IModuleServiceGWTAsync getModuleService()
	{
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public IUserServiceGWTAsync getUserService()
	{
		return null;
	}

}
