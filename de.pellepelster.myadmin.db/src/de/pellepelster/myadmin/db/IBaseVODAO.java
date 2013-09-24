/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. VOTypehis program and the accompanying materials
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
import de.pellepelster.myadmin.db.daos.IVODAOCallback;
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
	 * @param aggregateVOTypeype
	 * @return
	 */
	long aggregate(GenericFilterVO<?> genericFilterVO, String field, AggregateQuery.AGGREGATE_TYPE aggregateType);

	/**
	 * Persist a new VO
	 * 
	 * @param <VOType>
	 * @param vo
	 * @return
	 */
	<VOType extends IBaseVO> VOType create(VOType vo);

	/**
	 * Deletes a VO
	 * 
	 * @param <VOType>
	 * @param vo
	 */
	<VOType extends IBaseVO> void delete(VOType vo);

	/**
	 * Deletes all VOs of a certain type
	 * 
	 * @param clazz
	 */
	void deleteAll(Class<? extends IBaseVO> voClass);

	/**
	 * Gets all VOs by conditions in a specified order and paging
	 * 
	 * @param <VOType>
	 * @param genericFilterVO
	 * 
	 * @return
	 */
	<VOType extends IBaseVO> List<VOType> filter(GenericFilterVO<?> genericFilterVO);

	/**
	 * Gets all VOs for a certain type
	 * 
	 * @param <VOType>
	 * @param voClass
	 * @return
	 */
	<VOType extends IBaseVO> List<VOType> getAll(Class<VOType> voClass);

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
	 * @param <VOType>
	 * @param id
	 * @param voClass
	 * @return
	 */
	<VOType extends IBaseVO> VOType read(long id, Class<VOType> voClass);

	/**
	 * Reads a single vo by filter
	 * 
	 * @param genericFilterVO
	 * @return
	 */
	<VOType extends IBaseVO> VOType read(GenericFilterVO<VOType> genericFilterVO);

	/**
	 * Saves an VO
	 * 
	 * @param <VOType>
	 * @param vo
	 * @return
	 */
	<VOType extends IBaseVO> VOType save(VOType vo);

	<VOType extends IBaseVO> List<IVODAOCallback> getVODAOCallbacks();
}
