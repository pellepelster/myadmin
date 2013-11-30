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
package de.pellepelster.myadmin.client.core.utils;

import java.util.List;
import java.util.Map;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;

public class HierarchicalUtils
{
	public static IHierarchicalVO getParentVO(Map<String, String> parameters, IBaseEntityService baseEntityService)
	{

		if (parameters.get(IHierarchicalVO.FIELD_PARENT_CLASSNAME.getAttributeName()) != null
				&& parameters.get(IHierarchicalVO.FIELD_PARENT_ID.getAttributeName()) != null)
		{
			String parentVOClassName = parameters.get(IHierarchicalVO.FIELD_PARENT_CLASSNAME.getAttributeName());
			Long parentVOID = Long.parseLong(parameters.get(IHierarchicalVO.FIELD_PARENT_ID.getAttributeName()));
			GenericFilterVO<IBaseVO> genericFilterVO = ClientGenericFilterBuilder.createGenericFilter(parentVOClassName).getGenericFilter();
			genericFilterVO.addCriteria(IBaseVO.FIELD_ID, parentVOID);

			List<IBaseVO> result = baseEntityService.filter(genericFilterVO);

			if (result.size() == 1 && result.get(0) instanceof IHierarchicalVO)
			{
				IHierarchicalVO parentVO = (IHierarchicalVO) result.get(0);
				return parentVO;
			}

		}

		return null;
	}
}
