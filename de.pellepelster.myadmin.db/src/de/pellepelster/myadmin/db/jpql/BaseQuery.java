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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.jpql.LogicalOperatorVO;
import de.pellepelster.myadmin.client.base.jpql.RelationalOperator;
import de.pellepelster.myadmin.db.IBaseEntity;
import de.pellepelster.myadmin.db.jpql.expression.ConditionalExpression;
import de.pellepelster.myadmin.db.jpql.expression.IConditionalExpression;
import de.pellepelster.myadmin.db.jpql.expression.NamedParameterExpressionObject;

/**
 * Base functionality for constructing JPQL query construction
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public abstract class BaseQuery
{

	private final LogicalOperatorVO logicalOperator;

	private IAliasProvider aliasProvider = new IAliasProvider()
	{

		private int aliasCounter = -1;

		/** {@inheritDoc} */
		@Override
		public String getNewAlias()
		{
			aliasCounter++;
			return String.format("x%d", aliasCounter);
		}
	};

	/** All entities involved in the query */
	protected final Map<Class<? extends IBaseEntity>, Entity> entities = new HashMap<Class<? extends IBaseEntity>, Entity>();

	/** Main (first) entity for this query */
	protected final Entity mainEntity;

	/**
	 * Constructor for <code>BaseQuery</code>
	 * 
	 * @param entityClass
	 *            main entity for this query
	 */
	public BaseQuery(Class<? extends IBaseEntity> entityClass, LogicalOperatorVO logicalOperator)
	{
		mainEntity = addEntity(entityClass);
		this.logicalOperator = logicalOperator;
	}

	/**
	 * Adds an entity to this query
	 * 
	 * @param entityClass
	 */
	protected Entity addEntity(Class<? extends IBaseEntity> entityClass)
	{
		if (!entities.containsKey(entityClass))
		{
			Entity entity = new Entity(getAliasProvider(), entityClass);
			entities.put(entityClass, entity);
		}

		return entities.get(entityClass);

	}

	public void addWhereCondition(IAttributeDescriptor<?> attributeDescriptor, Object value, RelationalOperator relationalOperator)
	{
		addWhereCondition(attributeDescriptor.getAttributeName(), value, relationalOperator);
	}

	public void addWhereCondition(IAttributeDescriptor<?> attributeDescriptor, Object value)
	{
		addWhereCondition(attributeDescriptor.getAttributeName(), value);
	}

	/**
	 * Adds a criteria to the main entity
	 * 
	 * @param conditionalExpression
	 */
	public void addWhereCondition(IConditionalExpression conditionalExpression)
	{
		mainEntity.getConditionalExpressions().add(conditionalExpression);
	}

	/**
	 * Adds a criteria to the main entity
	 * 
	 * @param field
	 * @param value
	 */
	public void addWhereCondition(String field, Object value)
	{
		mainEntity.getConditionalExpressions().add(new ConditionalExpression(field, value));
	}

	public void addWhereCondition(String field, Object value, RelationalOperator relationalOperator)
	{
		mainEntity.getConditionalExpressions().add(new ConditionalExpression(field, value, relationalOperator));
	}

	/**
	 * Adds a list of criteria to the main entity
	 * 
	 * @param conditionalExpressions
	 */
	public void addWhereConditions(List<IConditionalExpression> conditionalExpressions)
	{
		mainEntity.getConditionalExpressions().addAll(conditionalExpressions);
	}

	protected IAliasProvider getAliasProvider()
	{
		return aliasProvider;
	}

	public Class<? extends IBaseEntity> getEntityClass()
	{
		return mainEntity.getEntityClass();
	}

	/**
	 * Returns all entities and their aliases involved in this query, comma
	 * delimited
	 * 
	 * @return
	 */
	protected String getFromClause()
	{
		String result = "";
		String delimiter = "";

		for (Map.Entry<Class<? extends IBaseEntity>, Entity> entry : entities.entrySet())
		{
			result += delimiter + "" + entry.getKey().getSimpleName() + " " + entry.getValue().getAlias();
			delimiter = ", ";
		}

		return result;
	}

	/**
	 * Returns a list of all named parameters involved in this query
	 * 
	 * @return
	 */
	public List<NamedParameterExpressionObject> getNamedParameters()
	{
		ArrayList<NamedParameterExpressionObject> result = new ArrayList<NamedParameterExpressionObject>();

		for (Map.Entry<Class<? extends IBaseEntity>, Entity> entry : entities.entrySet())
		{
			for (IConditionalExpression whereCondition : entry.getValue().getConditionalExpressions())
			{
				if (whereCondition.containsNamedParameter())
				{
					result.add(whereCondition.getNamedParameterObject());
				}
			}
		}

		return result;
	}

	/**
	 * Returns all criteria for this query
	 * 
	 * @return
	 */
	protected String getWhereClause()
	{

		if (hasWhereConditions())
		{
			String result = "";
			Boolean first = true;

			for (Map.Entry<Class<? extends IBaseEntity>, Entity> entry : entities.entrySet())
			{
				for (IConditionalExpression conditionalExpression : entry.getValue().getConditionalExpressions())
				{
					if (first)
					{
						result += conditionalExpression.getJPQL(entry.getValue());
					}
					else
					{
						result += " " + logicalOperator.toString() + " " + conditionalExpression.getJPQL(entry.getValue());
					}

					first = false;
				}
			}

			return String.format("WHERE %s", result);
		}
		else
		{
			return "";
		}
	}

	/**
	 * Returns true if at least on criteria exists for this query
	 * 
	 * @return
	 */
	private boolean hasWhereConditions()
	{
		for (Map.Entry<Class<? extends IBaseEntity>, Entity> entry : entities.entrySet())
		{
			if (!entry.getValue().getConditionalExpressions().isEmpty())
			{
				return true;

			}
		}

		return false;
	}
}
