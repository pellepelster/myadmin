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
 * Represents a delete JPQL query
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class DeleteQuery extends BaseQuery
{

	public DeleteQuery(Class<? extends IBaseEntity> entityClass, LogicalOperatorVO logicalOperator)
	{
		super(entityClass, logicalOperator);
	}

	public DeleteQuery(Class<? extends IBaseEntity> entityClass)
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
		String result = String.format("DELETE FROM %s %s", getFromClause(), getWhereClause());

		return result.trim();
	}

}
