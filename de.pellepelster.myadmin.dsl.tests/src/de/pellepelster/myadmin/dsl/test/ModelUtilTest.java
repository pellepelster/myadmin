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
import de.pellepelster.myadmin.dsl.util.ModelUtil;

public class ModelUtilTest extends BaseModelTest
{

	@Test
	public void testCreateUpdateOrCreateRootPackageEmptyModel()
	{
		Model model = getModel("model/EmptyModel.msl");

		Assert.assertFalse(ModelUtil.hasSingleRootPackage(model));

		ModelUtil.createUpdateOrCreateRootPackage(model, "a.b.c");

		Assert.assertTrue(ModelUtil.hasSingleRootPackage(model));
		Assert.assertEquals("a.b.c", ModelUtil.getSingleRootPackage(model).getName());
	}

	@Test
	public void testCreateUpdateOrCreateRootPackageSingleRootPackageModel()
	{
		Model model = getModel("model/SingleRootPackageModel.msl");

		Assert.assertTrue(ModelUtil.hasSingleRootPackage(model));
		Assert.assertEquals("a.b.c", ModelUtil.getSingleRootPackage(model).getName());

		ModelUtil.createUpdateOrCreateRootPackage(model, "x.x.x");

		Assert.assertTrue(ModelUtil.hasSingleRootPackage(model));
		Assert.assertEquals("x.x.x", ModelUtil.getSingleRootPackage(model).getName());
	}

}
