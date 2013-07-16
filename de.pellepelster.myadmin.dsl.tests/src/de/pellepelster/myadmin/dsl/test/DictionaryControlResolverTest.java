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

import org.junit.BeforeClass;

import de.pellepelster.myadmin.dsl.MyAdminDslStandaloneSetup;
import de.pellepelster.myadmin.dsl.myAdminDsl.Model;
import de.pellepelster.myadmin.tools.SpringModelUtils;

public class DictionaryControlResolverTest
{

	private Model model = SpringModelUtils.getModel("classpath:model/DictionaryControlResolverTest.msl");

	@BeforeClass
	public static void init()
	{
		MyAdminDslStandaloneSetup.doSetup();
	}

}
