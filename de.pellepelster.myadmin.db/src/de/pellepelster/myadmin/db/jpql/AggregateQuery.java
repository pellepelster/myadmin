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

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.jpql.LogicalOperatorVO;
import de.pellepelster.myadmin.db.IBaseEntity;

/**
 * Represents a aggregation query
 * 
 * @author pelle
 * @version $Rev: 1030 $, $Date: 2011-04-27 17:34:07 +0200 (Wed, 27 Apr 2011) $
 * 
 */
public class AggregateQuery extends BaseQuery
{
	/**
	 * Aggregation type
	 */
	public enum AGGREGATE_TYPE
	{
		SUM
	}

	/** Field to aggregate */
	private final String aggregateField;

	/** Aggregate type for this query */
	private final AGGREGATE_TYPE aggregateType;

	public AggregateQuery(Class<? extends IBaseEntity> entityClass, IAttributeDescriptor<?> attributeDescriptor, AGGREGATE_TYPE aggregateType)
	{
		this(entityClass, attributeDescriptor.getAttributeName(), aggregateType);
	}

	/**
	 * Constructor for <code>SelectQuery</code>
	 * 
	 * @param entityClass
	 * @param aggregateField
	 * @param aggregateType
	 */
	public AggregateQuery(Class<? extends IBaseEntity> entityClass, String aggregateField, AGGREGATE_TYPE aggregateType)
	{
		super(entityClass, LogicalOperatorVO.AND);
		this.aggregateField = aggregateField;
		this.aggregateType = aggregateType;
	}

	/**
	 * Adds a fetch join for a field
	 * 
	 * @param field
	 * @return
	 */
	public Join addJoin(String field)
	{
		return null;
	}

	/**
	 * Returns the join clause for this query
	 * 
	 * @return
	 */
	private String getJoinClause()
	{
		String result = "";

		return result;
	}

	/**
	 * Returns the JPQL for this select query
	 * 
	 * @return
	 */
	public String getJPQL()
	{
		Entity entity = entities.values().iterator().next();

		String result = String.format("SELECT %s(%s.%s) FROM %s %s %s", aggregateType, entity.getAlias(), aggregateField, getFromClause(), getJoinClause(),
				getWhereClause());

		return result.trim().replaceAll("\\b\\s{2,}\\b", " ");
	}

}
