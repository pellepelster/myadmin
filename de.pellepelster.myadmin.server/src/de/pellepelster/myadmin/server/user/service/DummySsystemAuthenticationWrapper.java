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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

public class DummySsystemAuthenticationWrapper implements Authentication
{
	private static final long serialVersionUID = -2269602488488880007L;
	private static GrantedAuthority grantedAuthority = new GrantedAuthorityImpl(MyAdminUserDetailsService.SYSTEM_GROUP_NAME);

	@Override
	public Collection<GrantedAuthority> getAuthorities()
	{
		List<GrantedAuthority> grantedAuthories = new ArrayList<GrantedAuthority>();
		grantedAuthories.add(grantedAuthority);

		return grantedAuthories;
	}

	@Override
	public Object getCredentials()
	{
		return MyAdminUserDetailsService.SYSTEM_USER_NAME;
	}

	@Override
	public Object getDetails()
	{
		return MyAdminUserDetailsService.SYSTEM_USER_NAME;
	}

	@Override
	public String getName()
	{
		return MyAdminUserDetailsService.SYSTEM_USER_NAME;
	}

	@Override
	public Object getPrincipal()
	{
		return null;
	}

	@Override
	public boolean isAuthenticated()
	{
		return true;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException
	{
	}

}
