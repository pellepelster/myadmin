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
	private Model model = SpringModelUtils.getModel("classpath:model/SingleRootPackageModel.msl");

	@Test
	public void testQueryRootPackages()
	{

		Assert.assertTrue(ModelQuery.createQuery(this.model).getRootPackages().hasOnePackage());

		Assert.assertEquals("a.b.c", ModelQuery.createQuery(this.model).getRootPackages().getSinglePackage().getName());
	}
}
