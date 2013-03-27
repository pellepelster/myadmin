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
package de.pellepelster.myadmin.db;

import java.util.List;
import java.util.Map;

import de.pellepelster.myadmin.db.jpql.AggregateQuery.AGGREGATE_TYPE;
import de.pellepelster.myadmin.db.jpql.SelectQuery;
import de.pellepelster.myadmin.db.jpql.expression.IConditionalExpression;

/**
 * DAO for entity CRUD operations
 * 
 * @author pelle
 * @version $Rev: 1030 $, $Date: 2011-04-27 17:34:07 +0200 (Wed, 27 Apr 2011) $
 */
public interface IBaseDAO
{

	/**
	 * Aggregates f field for all entities matching criteria
	 * 
	 * @param <T>
	 * @param conditionalExpressions
	 * @param entityClass
	 * @param field
	 * @param aggregateType
	 * @return
	 */
	<T extends IBaseEntity> long aggregate(List<IConditionalExpression> conditionalExpressions, Class<T> entityClass, String field, AGGREGATE_TYPE aggregateType);

	/**
	 * Persist a new entity
	 * 
	 * @param <T>
	 * @param entity
	 * @return
	 */
	<T extends IBaseEntity> T create(T entity);

	/**
	 * Deletes an entity
	 * 
	 * @param <T>
	 * @param entity
	 */
	<T extends IBaseEntity> void delete(T entity);

	/**
	 * Deletes all entity of a certain type
	 * 
	 * @param entityClass
	 */
	void deleteAll(Class<? extends IBaseEntity> entityClass);

	// /**
	// * @see IBaseDAO#filter(List, Map, List, int, int, Class)
	// */
	// <T extends IBaseEntity> List<T> filter(IConditionalExpression
	// conditionalExpression, Class<T> entityClass);
	//
	// /**
	// * @see IBaseDAO#filter(List, Map, List, int, int, Class)
	// */
	// <T extends IBaseEntity> List<T> filter(List<IConditionalExpression>
	// conditionalExpressions, Class<T> entityClass);
	//
	// /**
	// * @see IBaseDAO#filter(List, Map, List, int, int, Class)
	// */
	// <T extends IBaseEntity> List<T> filter(List<IConditionalExpression>
	// conditionalExpressions,
	// Map<String, ORDER_DIRECTION> orderBy, Class<T> entityClass);
	//
	// /**
	// * Gets entities by conditions
	// *
	// * @param <T>
	// * @param conditionalExpressions
	// * @param orderBy
	// * @param joins
	// * @param start
	// * @param limit
	// * @param entityClass
	// * @return
	// */
	// <T extends IBaseEntity> List<T> filter(List<IConditionalExpression>
	// conditionalExpressions,
	// Map<String, ORDER_DIRECTION> orderBy, List<String> joins, int start, int
	// limit, Class<T> entityClass);

	/**
	 * @see IBaseDAO#filter(List, Map, List, int, int, Class)
	 */
	<T extends IBaseEntity> List<T> filter(SelectQuery selectQuery, int start, int limit);

	/**
	 * Gets all entities of a certain type
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	<T extends IBaseEntity> List<T> getAll(Class<T> entityClass);

	/**
	 * Returns the number of entities matching criteria
	 * 
	 * @param entityClass
	 * @param conditionalExpressions
	 * @return
	 */
	long getCount(Class<? extends IBaseEntity> entityClass, List<IConditionalExpression> conditionalExpressions);

	/**
	 * @see IBaseDAO#getCount(Class, List)
	 */
	<T extends IBaseEntity> T read(long id, Class<T> entityClass);

	/**
	 * Saves an entity
	 * 
	 * @param <T>
	 * @param entity
	 * @return
	 */
	<T extends IBaseEntity> T save(T entity);
}
