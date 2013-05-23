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

import de.pellepelster.myadmin.server.SystemProperties;

public class MyAdminAccessDecisionManager extends AffirmativeBased
{

	private boolean isAuthorizationDisabled = false;

	public MyAdminAccessDecisionManager(@SuppressWarnings("rawtypes") List<AccessDecisionVoter> decisionVoters)
	{
		super(decisionVoters);
		this.isAuthorizationDisabled = (System.getProperty(SystemProperties.AUTHORIZATION_DISABLE) != null && System.getProperty(
				SystemProperties.AUTHORIZATION_DISABLE).equals("true"));
	}

	/** {@inheritDoc} */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException
	{
		if (this.isAuthorizationDisabled)
		{
			if (SecurityContextHolder.getContext().getAuthentication() == null)
			{
				SecurityContextHolder.getContext().setAuthentication(new SystemUserAuthenticationToken());
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
