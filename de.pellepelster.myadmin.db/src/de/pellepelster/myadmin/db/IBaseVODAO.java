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

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.db.jpql.AggregateQuery;

/**
 * Base DAO for VO CRUD operations
 * 
 * @author pelle
 * @version $Rev$, $Date$
 */
public interface IBaseVODAO
{

	/**
	 * Aggregates a field
	 * 
	 * @param voClass
	 * @param field
	 * @param aggregateType
	 * @return
	 */
	long aggregate(GenericFilterVO<?> genericFilterVO, String field, AggregateQuery.AGGREGATE_TYPE aggregateType);

	/**
	 * Persist a new VO
	 * 
	 * @param <T>
	 * @param vo
	 * @return
	 */
	<T extends IBaseVO> T create(T vo);

	/**
	 * Deletes a VO
	 * 
	 * @param <T>
	 * @param vo
	 */
	<T extends IBaseVO> void delete(T vo);

	/**
	 * Deletes all VOs of a certain type
	 * 
	 * @param clazz
	 */
	void deleteAll(Class<? extends IBaseVO> voClass);

	/**
	 * Gets all VOs by conditions in a specified order and paging
	 * 
	 * @param <T>
	 * @param genericFilterVO
	 * 
	 * @return
	 */
	<T extends IBaseVO> List<T> filter(GenericFilterVO<?> genericFilterVO);

	/**
	 * Gets all VOs for a certain type
	 * 
	 * @param <T>
	 * @param voClass
	 * @return
	 */
	<T extends IBaseVO> List<T> getAll(Class<T> voClass);

	/**
	 * Convenience for {@link IBaseVODAO#getCount(Class, List)}
	 * 
	 * @param voClass
	 * @return
	 */
	long getCount(Class<? extends IBaseVO> voClass);

	/**
	 * Gets the count for a VO type matching criteria
	 * 
	 * @param genericFilterVO
	 * @return
	 */
	long getCount(GenericFilterVO<?> genericFilterVO);

	/**
	 * Gets a single VO by id
	 * 
	 * @param <T>
	 * @param id
	 * @param voClass
	 * @return
	 */
	<T extends IBaseVO> T read(long id, Class<T> voClass);

	/**
	 * Reads a single vo by filter
	 * 
	 * @param genericFilterVO
	 * @return
	 */
	<T extends IBaseVO> T read(GenericFilterVO<T> genericFilterVO);
	
	
	/**
	 * Saves an VO
	 * 
	 * @param <T>
	 * @param vo
	 * @return
	 */
	<T extends IBaseVO> T save(T vo);
}
