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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.jpql.LogicalOperatorVO;
import de.pellepelster.myadmin.client.base.jpql.OrderClauseVO.ORDER_DIRECTION;
import de.pellepelster.myadmin.db.IBaseEntity;

/**
 * Abstraction for JPQL select queries
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class SelectQuery extends BaseQuery
{

	private final List<OrderClause> orderByClauses = new ArrayList<OrderClause>();

	public SelectQuery(Class<? extends IBaseEntity> entityClass, LogicalOperatorVO logicalOperator)
	{
		super(entityClass, logicalOperator);
	}

	public SelectQuery(Class<? extends IBaseEntity> entityClass)
	{
		this(entityClass, LogicalOperatorVO.AND);
	}

	public Join addJoin(IAttributeDescriptor<?> attributeDescriptor)
	{
		return mainEntity.addJoin(attributeDescriptor.getAttributeName());
	}

	/**
	 * Adds a fetch join for a field
	 * 
	 * @param field
	 * @return
	 */
	public Join addJoin(String field)
	{
		return mainEntity.addJoin(field);
	}

	public void addOrderBy(IAttributeDescriptor<?> attributeDescriptor, ORDER_DIRECTION orderType)
	{
		orderByClauses.add(new OrderClause(mainEntity, attributeDescriptor.getAttributeName(), orderType));
	}

	/**
	 * Adds an order by to an field
	 * 
	 * @param entityClass
	 * @param field
	 * @param orderType
	 */
	public void addOrderBy(String field, ORDER_DIRECTION orderType)
	{
		orderByClauses.add(new OrderClause(mainEntity, field, orderType));
	}

	/**
	 * Returns the join clause for this query
	 * 
	 * @return
	 */
	private String getJoinClause()
	{
		String result = "";
		String delimiter = "";

		for (Map.Entry<Class<? extends IBaseEntity>, Entity> entityEntry : entities.entrySet())
		{

			for (Join join : entityEntry.getValue().getJoins())
			{
				result += delimiter
						+ String.format("%s %s.%s %s %s", join.getJoinType(), entityEntry.getValue().getAlias(), join.getField(), join.getAlias(),
								join.getJoinClause());
				delimiter = " ";
			}
		}
		return result;
	}

	/**
	 * Returns the JPQL for this select query
	 * 
	 * @return
	 */
	public String getJPQL()
	{

		String result = String.format("SELECT %s FROM %s %s %s %s", getSelectClause(), getFromClause(), getJoinClause(), getWhereClause(), getOrderClause());

		return result.trim().replaceAll("\\b\\s{2,}\\b", " ");
	}

	/**
	 * Returns the order clauses for this query
	 * 
	 * @return
	 */
	private String getOrderClause()
	{
		String result = "";
		String delimiter = " ORDER BY ";

		for (OrderClause orderClause : orderByClauses)
		{
			result += delimiter + orderClause.getJPQL();
			delimiter = ", ";
		}

		return result;
	}

	/**
	 * Returns the select clauses for this query
	 * 
	 * @return
	 */
	private String getSelectClause()
	{
		String result = "";
		String delimiter = "";

		for (Map.Entry<Class<? extends IBaseEntity>, Entity> entry : entities.entrySet())
		{
			result += delimiter + entry.getValue().getAlias();
			delimiter = ", ";
		}

		return result;
	}

	public void removeJoin(String field)
	{
		mainEntity.removeJoin(field);
	}

}
