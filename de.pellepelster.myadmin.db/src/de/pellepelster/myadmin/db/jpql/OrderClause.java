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

import de.pellepelster.myadmin.client.base.jpql.OrderClauseVO.ORDER_DIRECTION;

/**
 * Represents a order clause for a JPQL query
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class OrderClause
{
	/** Field to order */
	private final String field;

	/** Order direction */
	private final ORDER_DIRECTION orderType;

	/** Entity this order applies to */
	private final IEntity parentEntity;

	/**
	 * Constructor for <code>OrderClause</code>
	 * 
	 * @param entityClass
	 * @param field
	 * @param orderType
	 */
	public OrderClause(IEntity parentEntity, String field, ORDER_DIRECTION orderType)
	{
		this.parentEntity = parentEntity;
		this.field = field;
		this.orderType = orderType;
	}

	/**
	 * The field to order
	 * 
	 * @return
	 */
	public String getField()
	{
		return field;
	}

	/**
	 * JPQL representing this order clause
	 * 
	 * @return
	 */
	public String getJPQL()
	{
		if (orderType == ORDER_DIRECTION.ASC)
		{

			return String.format("%s.%s", parentEntity.getAlias(), field);
		}
		else
		{
			return String.format("%s.%s DESC", parentEntity.getAlias(), field);
		}
	}

	/**
	 * The order direction
	 * 
	 * @return
	 */
	public ORDER_DIRECTION getOrderType()
	{
		return orderType;
	}

}
