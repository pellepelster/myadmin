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
import java.util.List;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class MyAdminAccessDecisionManager extends AffirmativeBased
{
	public static final String SYSTEM_PROPERTY_DISABLE_AUTHORIZATION = "disable.authorization";

	private boolean isAuthorizationDisabled = false;

	public MyAdminAccessDecisionManager(List<AccessDecisionVoter> decisionVoters)
	{
		super(decisionVoters);
		this.isAuthorizationDisabled = (System.getProperty(SYSTEM_PROPERTY_DISABLE_AUTHORIZATION) != null && System.getProperty(
				SYSTEM_PROPERTY_DISABLE_AUTHORIZATION).equals("true"));
	}

	/** {@inheritDoc} */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException
	{
		if (this.isAuthorizationDisabled)
		{
			if (SecurityContextHolder.getContext().getAuthentication() == null)
			{
				SecurityContextHolder.getContext().setAuthentication(new DummySystemAuthenticationToken());
			}
		}
		else
		{
			super.decide(authentication, object, configAttributes);
		}
	}

	@Override
	public boolean supports(Class<?> clazz)
	{
		return super.supports(clazz);
	}

	@Override
	public boolean supports(ConfigAttribute attribute)
	{
		return super.supports(attribute);
	}
}
