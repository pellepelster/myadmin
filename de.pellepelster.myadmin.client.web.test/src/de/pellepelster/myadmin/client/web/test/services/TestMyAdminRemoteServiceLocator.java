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
package de.pellepelster.myadmin.client.web.test.services;

import de.pellepelster.myadmin.client.web.IMyAdminGWTRemoteServiceLocator;
import de.pellepelster.myadmin.client.web.ISystemServiceGWTAsync;
import de.pellepelster.myadmin.client.web.services.IHierachicalServiceGWTAsync;
import de.pellepelster.myadmin.client.web.services.IModuleServiceGWTAsync;
import de.pellepelster.myadmin.client.web.services.IUserServiceGWTAsync;
import de.pellepelster.myadmin.client.web.services.search.IDictionarySearchServiceGWTAsync;
import de.pellepelster.myadmin.client.web.services.vo.IBaseEntityServiceGWTAsync;

public class TestMyAdminRemoteServiceLocator implements IMyAdminGWTRemoteServiceLocator
{

	private final IBaseEntityServiceGWTAsync baseEntityServiceGWTAsync;
	private final IHierachicalServiceGWTAsync hierachicalServiceGWTAsync;

	public TestMyAdminRemoteServiceLocator()
	{
		super();
		this.baseEntityServiceGWTAsync = new TestBaseEntityServiceGWTAsync();
		this.hierachicalServiceGWTAsync = new TestHierarchicalServiceGWTAsync();
	}

	/** {@inheritDoc} */
	@Override
	public IBaseEntityServiceGWTAsync getBaseEntityService()
	{
		return this.baseEntityServiceGWTAsync;
	}

	/** {@inheritDoc} */
	@Override
	public IHierachicalServiceGWTAsync getHierachicalService()
	{
		return this.hierachicalServiceGWTAsync;
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

	@Override
	public ISystemServiceGWTAsync getSystemService()
	{
		return null;
	}

	@Override
	public IDictionarySearchServiceGWTAsync getDictionarySearchService()
	{
		return null;
	}

}
