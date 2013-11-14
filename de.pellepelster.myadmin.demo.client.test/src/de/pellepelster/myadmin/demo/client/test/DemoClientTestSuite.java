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
package de.pellepelster.myadmin.demo.client.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.google.gwt.junit.tools.GWTTestSuite;

public class DemoClientTestSuite extends GWTTestSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite(DemoClientTestSuite.class.getName());

		suite.addTestSuite(DemoClientCityTest.class);
		suite.addTestSuite(DemoClientDictionary1Test.class);
		suite.addTestSuite(DemoClientNavigationTest.class);

		return suite;
	}
}