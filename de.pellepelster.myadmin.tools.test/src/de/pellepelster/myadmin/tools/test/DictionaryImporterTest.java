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
package de.pellepelster.myadmin.tools.test;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.core.io.Resource;

import de.pellepelster.myadmin.tools.SpringModelUtils;

public class DictionaryImporterTest
{

	@Test
	public void testTestGetDictionary1()
	{
		Resource modelResource = SpringModelUtils.getResource("file:model/TestModel1.msl");
		try
		{
			Assert.assertNotNull(modelResource.getInputStream());
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}

	}

}
