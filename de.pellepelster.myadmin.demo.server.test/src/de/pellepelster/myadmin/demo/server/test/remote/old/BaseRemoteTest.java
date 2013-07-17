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
package de.pellepelster.myadmin.demo.server.test.remote.old;

import junit.framework.TestCase;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import de.pellepelster.myadmin.client.web.MyAdminRemoteServiceLocator;

public class BaseRemoteTest extends TestCase
{

	public static void initApplicationContext()
	{
		System.setProperty("remote.server", "localhost");
		System.setProperty("remote.port", "8080");
		System.setProperty("remote.path", "de.pellepelster.myadmin.demo/remote");

		SecurityContextImpl sc = new SecurityContextImpl();
		Authentication auth = new UsernamePasswordAuthenticationToken("system", "system");
		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);

		ApplicationContextProvider.getInstance().init(new String[] { "MyAdminDemoTestApplicationContext.xml", "MyAdminClientServices-gen.xml" });
		MyAdminRemoteServiceLocator.getInstance().init(ApplicationContextProvider.getInstance());

	}
}
