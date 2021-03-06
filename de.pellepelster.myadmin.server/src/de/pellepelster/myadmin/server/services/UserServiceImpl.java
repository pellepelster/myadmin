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

import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.user.IMyAdminUserClientDetails;
import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;
import de.pellepelster.myadmin.client.web.services.IUserService;
import de.pellepelster.myadmin.db.daos.BaseVODAO;
import de.pellepelster.myadmin.server.core.query.ServerGenericFilterBuilder;
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
	private BaseVODAO baseVODAO;

	private final static Logger LOG = Logger.getLogger(UserServiceImpl.class);

	@Override
	@Secured("IS_AUTHENTICATED_FULLY")
	public void changePassword(String arg0, String arg1)
	{
	}

	@Override
	public IMyAdminUserClientDetails getCurrentUser()
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

	public void setBaseVODAO(BaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

	public Boolean userNameExistsInternal(String username)
	{
		return this.baseVODAO.getCount(ServerGenericFilterBuilder.createGenericFilter(MyAdminUserVO.class).addCriteria(MyAdminUserVO.FIELD_USERNAME, username)
				.getGenericFilter()) > 0;
	}

	@Override
	public Boolean userNameExists(String username)
	{
		if (userNameExistsInternal(username))
		{
			LOG.debug(String.format("user name '%s' exists", username));

			return true;
		}
		else
		{
			LOG.debug(String.format("user name '%s' dost not exist", username));

			return false;
		}
	}

	@Override
	public MyAdminUserVO registerUser(String userName, String userEmail)
	{
		LOG.debug(String.format("register user '%s'", userName));

		if (userNameExists(userName))
		{
			return null;
		}
		else
		{
			MyAdminUserVO myAdminUser = new MyAdminUserVO();
			myAdminUser.setUserName(userName);
			myAdminUser.setUserMail(userEmail);

			myAdminUser = this.baseVODAO.create(myAdminUser);

			LOG.debug(String.format("user '%s' registered", userName));

			return myAdminUser;
		}
	}

	@Override
	public MyAdminUserVO getUserByName(String userName)
	{
		GenericFilterVO<MyAdminUserVO> genericFilter = ServerGenericFilterBuilder.createGenericFilter(MyAdminUserVO.class).addCriteria(MyAdminUserVO.FIELD_USERNAME, userName).getGenericFilter();
		genericFilter.addAssociation(MyAdminUserVO.FIELD_USERGROUPS);
				
		return this.baseVODAO.read(genericFilter);
	}
}
