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
package de.pellepelster.myadmin.server.user.service;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class MyAdminAccessDecisionManager implements AccessDecisionManager
{
	public static final String SYSTEM_PROPERTY_DISABLE_AUTHORIZATION = "disable.authorization";
	private boolean isAuthorizationDisabled = false;

	public MyAdminAccessDecisionManager()
	{
		isAuthorizationDisabled = (System.getProperty(SYSTEM_PROPERTY_DISABLE_AUTHORIZATION) != null && System.getProperty(
				SYSTEM_PROPERTY_DISABLE_AUTHORIZATION).equals("true"));
	}

	/** {@inheritDoc} */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException
	{
		if (isAuthorizationDisabled)
		{
			if (SecurityContextHolder.getContext().getAuthentication() == null)
			{
				SecurityContextHolder.getContext().setAuthentication(new DummySsystemAuthenticationWrapper());
			}
		}
		else
		{
			throw new AccessDeniedException("Access is denied");
		}
	}

	@Override
	public boolean supports(Class<?> clazz)
	{
		return true;
	}

	@Override
	public boolean supports(ConfigAttribute attribute)
	{
		return true;
	}
}
