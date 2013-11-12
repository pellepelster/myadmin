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

import java.util.List;

import javax.annotation.Resource;

import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.services.IModuleService;
import de.pellepelster.myadmin.db.daos.BaseVODAO;

public class ModuleServiceImpl implements IModuleService
{
	@Resource
	private BaseVODAO baseVODAO;

	/** {@inheritDoc} */
	@Override
	public List<ModuleNavigationVO> getModulesNavigation()
	{

		GenericFilterVO<ModuleNavigationVO> genericFilterVO = new GenericFilterVO<ModuleNavigationVO>(ModuleNavigationVO.class);
		genericFilterVO.addCriteria(ModuleNavigationVO.FIELD_PARENT.getAttributeName(), null);
		genericFilterVO.addAssociation(ModuleNavigationVO.FIELD_CHILDREN.getAttributeName());
		genericFilterVO.addAssociation(ModuleNavigationVO.FIELD_MODULE).addAssociation(ModuleVO.FIELD_MODULEDEFINITION);

		return this.baseVODAO.filter(genericFilterVO);
	}

	public void setBaseVODAO(BaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

}
