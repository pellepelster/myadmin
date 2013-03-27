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
import de.pellepelster.myadmin.db.IBaseVODAO;

public class ModuleServiceImpl implements IModuleService
{
	@Resource
	private IBaseVODAO baseVODAO;

	/** {@inheritDoc} */
	@Override
	public List<ModuleNavigationVO> getModulesNavigation()
	{

		GenericFilterVO<ModuleNavigationVO> genericFilterVO = new GenericFilterVO<ModuleNavigationVO>(ModuleNavigationVO.class);
		genericFilterVO.addCriteria(ModuleNavigationVO.FIELD_PARENT.getAttributeName(), null);
		genericFilterVO.addAssociation(ModuleNavigationVO.FIELD_CHILDREN.getAttributeName());
		genericFilterVO.addAssociation(ModuleNavigationVO.FIELD_MODULE).addAssociation(ModuleVO.FIELD_MODULEDEFINITION);

		return baseVODAO.filter(genericFilterVO);
	}

	/**
	 * Sets the {@link IBaseVODAO} implementation
	 * 
	 * @param baseDAO
	 */
	public void setBaseVODAO(IBaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

}
