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
package de.pellepelster.myadmin.db.jpql;

import de.pellepelster.myadmin.client.base.jpql.LogicalOperatorVO;
import de.pellepelster.myadmin.db.IBaseEntity;

/**
 * Represents a count query
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class CountQuery extends BaseQuery
{

	public CountQuery(Class<? extends IBaseEntity> entityClass, LogicalOperatorVO logicalOperator)
	{
		super(entityClass, logicalOperator);
	}

	public CountQuery(Class<? extends IBaseEntity> entityClass)
	{
		super(entityClass, LogicalOperatorVO.AND);
	}

	/**
	 * Returns the JPQL for this query
	 * 
	 * @return
	 */
	public String getJPQL()
	{
		if (entities.size() > 1)
		{
			throw new RuntimeException("to many entities for count query");
		}

		String result = String.format("SELECT COUNT(%s) FROM %s %s %s", mainEntity.getAlias(), mainEntity.getName(), mainEntity.getAlias(), getWhereClause());

		return result.trim();
	}
}
