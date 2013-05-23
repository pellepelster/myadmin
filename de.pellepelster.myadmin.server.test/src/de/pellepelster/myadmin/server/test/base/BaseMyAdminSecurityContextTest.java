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
package de.pellepelster.myadmin.server.test.base;

import java.util.Collections;

import org.junit.BeforeClass;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;
import de.pellepelster.myadmin.server.user.service.MyAdminUserDetails;

public abstract class BaseMyAdminSecurityContextTest extends BaseMyAdminJndiContextTest
{

	public static final String TESTUSER_NAME = "testuser";

	@BeforeClass
	public static void fakeAuthentication()
	{
		SecurityContextImpl sc = new SecurityContextImpl();

		MyAdminUserVO myAdminUserVO = new MyAdminUserVO();
		myAdminUserVO.setUserName(TESTUSER_NAME);

		@SuppressWarnings("unchecked")
		Authentication auth = new UsernamePasswordAuthenticationToken(new MyAdminUserDetails(myAdminUserVO), TESTUSER_NAME, Collections.EMPTY_LIST);
		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);
	}

}
