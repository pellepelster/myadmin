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

import junit.framework.Assert;

import org.junit.Test;

import de.pellepelster.myadmin.dsl.myAdminDsl.Model;
import de.pellepelster.myadmin.dsl.query.ModelQuery;
import de.pellepelster.myadmin.tools.SpringModelUtils;

public class ModelQueryTest
{

	@Test
	public void testQueryRootPackages()
	{
		Model model = SpringModelUtils.getModel("classpath:model/SingleRootPackageModel.msl");

		Assert.assertTrue(ModelQuery.createQuery(model).getRootPackages().hasExactlyOne());
		Assert.assertEquals("a.b", ModelQuery.createQuery(model).getRootPackages().getSinglePackage().getName());
	}

	@Test
	public void testGetPackagesByName()
	{
		Model model = SpringModelUtils.getModel("classpath:model/PackagesModel.msl");

		Assert.assertEquals("c.d", ModelQuery.createQuery(model).getPackageByName("a.b.c.d").getName());
		Assert.assertEquals("a.b", ModelQuery.createQuery(model).getPackageByName("a.b").getName());
	}

	@Test
	public void testGetAndCreatePackageByName()
	{
		Model model = SpringModelUtils.getModel("classpath:model/PackagesModel.msl");

		Assert.assertEquals("e.f", ModelQuery.createQuery(model).getAndCreatePackageByName("a.b.c.d.e.f").getName());
		Assert.assertEquals("e.f", ModelQuery.createQuery(model).getPackageByName("a.b.c.d.e.f").getName());

		Assert.assertEquals("a.b", ModelQuery.createQuery(model).getAndCreatePackageByName("a.b").getName());
		Assert.assertEquals("c.d", ModelQuery.createQuery(model).getAndCreatePackageByName("a.b.c.d").getName());

	}

	@Test
	public void testGetAndCreateSplitPackageByName()
	{
		Model model = SpringModelUtils.getModel("classpath:model/PackagesModel.msl");

		Assert.assertEquals("x", ModelQuery.createQuery(model).getAndCreatePackageByName("a.b.c.x").getName());
		Assert.assertEquals("c", ModelQuery.createQuery(model).getPackageByName("a.b.c").getName());
		Assert.assertEquals("d", ModelQuery.createQuery(model).getPackageByName("a.b.c.d").getName());
	}

}
