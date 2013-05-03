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

public class ModelQueryTest extends BaseModelTest
{

	@Test
	public void testQueryRootPackages()
	{
		Model model = getModel("model/SingleRootPackageModel.msl");

		Assert.assertTrue(ModelQuery.createQuery(model).getRootPackages().hasOnePackage());

		Assert.assertEquals("a.b.c", ModelQuery.createQuery(model).getRootPackages().getSinglePackage().getName());
	}
}
