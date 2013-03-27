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

import de.pellepelster.myadmin.db.IBaseEntity;
import de.pellepelster.myadmin.db.jpql.Join.JOIN_TYPE;
import de.pellepelster.myadmin.db.jpql.expression.ConditionalExpression;
import de.pellepelster.myadmin.db.jpql.expression.IConditionalExpression;

/**
 * Represents an entity for an JPQL query
 * 
 * @author pelle
 * @version $Rev: 662 $, $Date: 2010-08-30 20:58:04 +0200 (Mon, 30 Aug 2010) $
 * 
 */
public class Entity implements IEntity
{

	/** Conditional expressions for this entity */
	private final List<IConditionalExpression> conditionalExpressions = new ArrayList<IConditionalExpression>();

	/** Entity class */
	private final Class<? extends IBaseEntity> entityClass;;

	/** Joins for this entity */
	private final Map<String, Join> joins = new HashMap<String, Join>();

	private String alias;

	private IAliasProvider aliasProvider;

	/**
	 * Constructor for <code>Entity</code>
	 * 
	 * /**
	 * 
	 * @param aliasProvider
	 * @param entityClass
	 */
	public Entity(IAliasProvider aliasProvider, Class<? extends IBaseEntity> entityClass)
	{
		this.entityClass = entityClass;
		this.alias = aliasProvider.getNewAlias();
		this.aliasProvider = aliasProvider;

	}

	/**
	 * Adds a new fetch join for a field
	 * 
	 * @param field
	 * @return
	 */
	public Join addJoin(String field)
	{

		if (joins.containsKey(field))
		{
			return joins.get(field);
		}
		else
		{
			Join join = new Join(aliasProvider, JOIN_TYPE.LEFT, field);
			joins.put(field, join);

			return join;
		}
	}

	/**
	 * Adds a criteria to the query conditions
	 * 
	 * @param field
	 * @param value
	 */
	public void addWhereCondition(String field, Object value)
	{
		conditionalExpressions.add(new ConditionalExpression(field, value));
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Entity)
		{
			return ((Entity) obj).entityClass.equals(entityClass);
		}

		if (obj instanceof Class<?>)
		{
			return ((Class<?>) obj).equals(entityClass);
		}

		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String getAlias()
	{
		return alias;
	}

	/**
	 * Returns the criteria for this entity
	 * 
	 * @return
	 */
	public List<IConditionalExpression> getConditionalExpressions()
	{
		return conditionalExpressions;
	}

	/**
	 * The entity class
	 * 
	 * @return
	 */
	public Class<? extends IBaseEntity> getEntityClass()
	{
		return entityClass;
	}

	/**
	 * Joins for this query
	 * 
	 * @return
	 */
	public List<Join> getJoins()
	{
		return new ArrayList<Join>(joins.values());
	}

	/**
	 * Name for this entity
	 * 
	 * @return
	 */
	public String getName()
	{
		return entityClass.getSimpleName();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return entityClass.hashCode();
	}

	public void removeJoin(String field)
	{

		joins.remove(field);

	}

}
