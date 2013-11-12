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

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminGroupVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;
import de.pellepelster.myadmin.db.daos.BaseVODAO;

/**
 * Database based implementation of {@link UserDetailsService}
 * 
 * @author pelle
 * @version $Rev: 1069 $, $Date: 2011-05-15 20:58:42 +0200 (Sun, 15 May 2011) $
 * 
 */
public class MyAdminUserDetailsService implements UserDetailsService
{

	private static Logger logger = Logger.getLogger(MyAdminUserDetailsService.class);

	public final static String SYSTEM_USER_NAME = "system";

	public final static String SYSTEM_GROUP_NAME = "ROLE_ADMIN";

	@Autowired
	private BaseVODAO baseVODAO;

	/** {@inheritDoc} */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
	{

		logger.debug(String.format("looking up user '%s'", username));

		GenericFilterVO<MyAdminUserVO> filter = new GenericFilterVO<MyAdminUserVO>(MyAdminUserVO.class);
		filter.addAssociation(MyAdminUserVO.FIELD_USERGROUPS);
		filter.addCriteria(MyAdminUserVO.FIELD_USERNAME, username);

		List<MyAdminUserVO> users = this.baseVODAO.filter(filter);

		if (users.size() == 1)
		{

			MyAdminUserVO userVO = users.get(0);
			logger.debug(String.format("user '%s' found", userVO.getUserName()));

			return new MyAdminUserDetails(userVO);

		}
		else if (users.size() == 0 && SYSTEM_USER_NAME.equals(username))
		{
			GenericFilterVO<MyAdminGroupVO> groupFilter = new GenericFilterVO<MyAdminGroupVO>(MyAdminGroupVO.class);
			groupFilter.addCriteria(MyAdminGroupVO.FIELD_GROUPNAME, SYSTEM_GROUP_NAME);

			List<MyAdminGroupVO> result = this.baseVODAO.filter(groupFilter);

			MyAdminGroupVO systemGroupVO = null;
			if (result.isEmpty())
			{

				systemGroupVO = new MyAdminGroupVO();
				systemGroupVO.setGroupName(SYSTEM_GROUP_NAME);
				systemGroupVO = this.baseVODAO.create(systemGroupVO);

			}
			else
			{
				systemGroupVO = result.get(0);
			}

			MyAdminUserVO systemUserVO = new MyAdminUserVO();
			systemUserVO.setUserName(SYSTEM_USER_NAME);
			systemUserVO.setUserPassword(SYSTEM_USER_NAME);
			systemUserVO.getUserGroups().add(systemGroupVO);

			return new MyAdminUserDetails(this.baseVODAO.create(systemUserVO));

		}
		else
		{
			String message;
			if (users.size() > 1)
			{
				message = String.format("user '%s' found more than once (%d)", username, users.size());
			}
			else
			{
				message = String.format("user '%s' not found", username);
			}
			logger.debug(message);
			throw new UsernameNotFoundException(message);
		}

	}

	public void setBaseVODAO(BaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

}
