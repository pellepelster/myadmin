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

import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.web.services.IHierachicalService;
import de.pellepelster.myadmin.db.daos.BaseVODAO;
import de.pellepelster.myadmin.db.daos.IVODaoDecorator;
import de.pellepelster.myadmin.server.core.query.ServerGenericFilterBuilder;

public class HierarchicalVODecorator implements IVODaoDecorator
{
	@Autowired
	private BaseVODAO baseVODAO;

	@Autowired
	private IHierachicalService hierachicalService;

	/** {@inheritDoc} */
	@Override
	public void decorateVO(IBaseVO vo)
	{
		assert vo instanceof IHierarchicalVO : "vo has to be an IHierarchicalVO vo";
		IHierarchicalVO hierarchicalVO = (IHierarchicalVO) vo;

		if (hierarchicalVO.getParentClassName() != null && hierarchicalVO.getParentId() != null)
		{
			IHierarchicalVO parent = (IHierarchicalVO) this.baseVODAO.read(ServerGenericFilterBuilder.createGenericFilter(hierarchicalVO.getParentClassName())
					.addCriteria(IBaseVO.FIELD_ID, hierarchicalVO.getParentId()).getGenericFilter());
			hierarchicalVO.setParent(parent);
		}

		hierarchicalVO.setHasChildren(this.hierachicalService.hasChildren(vo.getClass().getName(), vo.getId()));
	}

	public void setBaseVODAO(BaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

	public void setHierachicalService(IHierachicalService hierachicalService)
	{
		this.hierachicalService = hierachicalService;
	}

	@Override
	public boolean supports(Class<? extends IBaseVO> voClass)
	{
		return IHierarchicalVO.class.isAssignableFrom(voClass);
	}

}
