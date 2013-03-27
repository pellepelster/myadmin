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
import org.springframework.security.core.context.SecurityContextHolder;

import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;
import de.pellepelster.myadmin.client.web.services.IUserService;
import de.pellepelster.myadmin.db.IBaseVODAO;

/**
 * Implenetation for {@link IUserService}
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
	public void changePassword(String arg0, String arg1)
	{
	}

	@Override
	public MyAdminUserVO getCurrentUser()
	{
		try
		{
			MyAdminUserVO user = (MyAdminUserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyAdminUserVO userVO = baseVODAO.read(user.getId(), MyAdminUserVO.class);

			return userVO;
		}
		catch (Exception e)
		{
			LOG.error("error reading user", e);
			throw new RuntimeException(e);
		}
	}

	public void setBaseVODAO(IBaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}
}
