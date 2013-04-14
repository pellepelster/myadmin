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
package de.pellepelster.myadmin.demo.server.test.remote;

import java.util.List;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.web.MyAdminRemoteServiceLocator;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.demo.client.web.entities.CountryVO;

@SuppressWarnings({ "unchecked", "rawtypes" })
public final class BaseEntityServiceRemoteTest extends TestCase
{

	@BeforeClass
	public static void init()
	{
		System.setProperty("remote.server", "localhost");
		System.setProperty("remote.user", "");
		System.setProperty("remote.password", "");
		System.setProperty("remote.port", "8080");
		System.setProperty("remote.path", "de.pellepelster.myadmin.demo/MyAdminDemoRemote");

		ApplicationContextProvider.getInstance().init(new String[] { "MyAdminDemoTestApplicationContext.xml", "MyAdminClientServices-gen.xml" });
		// MyAdminRemoteServiceLocator.getInstance().init(ApplicationContextProvider.getInstance());

	}

	@Test
	public void testBaseEntityServiceRemote()
	{
		init();
		IBaseEntityService baseEntityServiceRemote = MyAdminRemoteServiceLocator.getInstance().getBaseEntityService();

		GenericFilterVO genericFilterVO = new GenericFilterVO(ModuleNavigationVO.class.getName());
		genericFilterVO.addCriteria(ModuleNavigationVO.FIELD_PARENT.getAttributeName(), null);

		List<IBaseVO> result = baseEntityServiceRemote.filter(genericFilterVO);

		assertEquals(1, result.size());

	}

	@Test
	public void testCountry()
	{
		init();
		IBaseEntityService baseEntityServiceRemote = MyAdminRemoteServiceLocator.getInstance().getBaseEntityService();

		GenericFilterVO genericFilterVO = new GenericFilterVO(CountryVO.class.getName());

		List<IBaseVO> result = baseEntityServiceRemote.filter(genericFilterVO);

		assertEquals(1, result.size());

	}

}
