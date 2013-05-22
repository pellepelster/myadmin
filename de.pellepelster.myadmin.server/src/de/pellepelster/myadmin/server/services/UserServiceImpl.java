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
package de.pellepelster.myadmin.server.services;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;

import de.pellepelster.myadmin.client.base.jpql.GenericFilterFactory;
import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;
import de.pellepelster.myadmin.client.web.services.IUserService;
import de.pellepelster.myadmin.db.IBaseVODAO;
import de.pellepelster.myadmin.server.core.user.IMyAdminUserDetails;
import de.pellepelster.myadmin.server.user.service.MyAdminUserDetails;

/**
 * Implementation for {@link IUserService}
 * 
 * @author Christian Pelster
 * 
 */
public class UserServiceImpl implements IUserService
{
	@Resource
	private IBaseVODAO baseVODAO;

	private final static Logger LOG = Logger.getLogger(UserServiceImpl.class);

	@Override
	@Secured("IS_AUTHENTICATED_FULLY")
	public void changePassword(String arg0, String arg1)
	{
	}

	@Override
	public IMyAdminUserDetails getCurrentUser()
	{
		try
		{
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyAdminUserDetails userDetails = null;

			if (principal instanceof MyAdminUserDetails)
			{
				userDetails = (MyAdminUserDetails) principal;
			}
			else
			{
				throw new RuntimeException(String.format("expected '%s' but got '%s'", MyAdminUserDetails.class.getName(), principal.getClass().getName()));
			}

			return userDetails;
		}
		catch (Exception e)
		{
			LOG.error("error getting current user", e);
			throw new RuntimeException(e);
		}
	}

	public void setBaseVODAO(IBaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

	@Override
	public Boolean userNameExists(String username)
	{
		return this.baseVODAO.getCount(GenericFilterFactory.createGenericFilter(MyAdminUserVO.class, MyAdminUserVO.FIELD_USERNAME, username)) > 0;
	}

	@Override
	public Boolean registerUser(String username, String email)
	{
		LOG.debug(String.format("register user '%s'", username));

		if (userNameExists(username))
		{
			LOG.debug(String.format("user '%s' already exists", username));

			return false;
		}
		else
		{
			MyAdminUserVO myAdminUser = new MyAdminUserVO();
			myAdminUser.setUserName(username);
			myAdminUser.setUserMail(email);

			this.baseVODAO.create(myAdminUser);

			LOG.debug(String.format("user '%s' registered", username));

			return true;
		}
	}
}
