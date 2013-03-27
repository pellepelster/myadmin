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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminGroupVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;

public class UserVOWrapper implements UserDetails
{

	private static final long serialVersionUID = -3973295031086748470L;
	private final MyAdminUserVO userVO;

	public UserVOWrapper(MyAdminUserVO userVO)
	{
		super();
		this.userVO = userVO;
	}

	/** {@inheritDoc} */
	@Override
	public Collection<GrantedAuthority> getAuthorities()
	{

		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();

		for (MyAdminGroupVO groupVO : userVO.getUserGroups())
		{
			result.add(new GrantedAuthorityImpl(groupVO.getGroupName()));
		}

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String getPassword()
	{
		return userVO.getUserPassword();
	}

	/** {@inheritDoc} */
	@Override
	public String getUsername()
	{
		return userVO.getUserName();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEnabled()
	{
		return true;
	}

}
