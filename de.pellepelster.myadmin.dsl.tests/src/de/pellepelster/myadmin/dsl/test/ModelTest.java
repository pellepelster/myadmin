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

import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.Assert;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;
import org.osgi.framework.Bundle;

import de.pellepelster.myadmin.dsl.myAdminDsl.Model;
import de.pellepelster.myadmin.dsl.ui.ModelUiUtil;
import de.pellepelster.myadmin.dsl.util.ModelUtil;

public class ModelTest
{

	public Model getModel(String model)
	{
		Bundle bundle = Activator.getDefault().getBundle();
		URL fileURL = bundle.getResource(model);

		try
		{
			return ModelUiUtil.getModelFromFile(URI.createURI(fileURL.toURI().toString()));
		}
		catch (URISyntaxException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testModel()
	{
		Model model = getModel("model/EmptyModel.msl");
		Assert.assertFalse(ModelUtil.hasRootPackage(model));

		ModelUtil.createRootPackage(model, "a.b.c");

		Assert.assertTrue(ModelUtil.hasRootPackage(model));
	}
}
