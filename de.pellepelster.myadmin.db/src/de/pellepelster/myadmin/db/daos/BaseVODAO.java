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
package de.pellepelster.myadmin.db.daos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.db.IBaseEntity;
import de.pellepelster.myadmin.db.jpql.AggregateQuery.AGGREGATE_TYPE;
import de.pellepelster.myadmin.db.jpql.SelectQuery;
import de.pellepelster.myadmin.db.jpql.expression.IConditionalExpression;
import de.pellepelster.myadmin.db.util.BeanUtil;
import de.pellepelster.myadmin.db.util.CopyBean;
import de.pellepelster.myadmin.db.util.DBUtil;
import de.pellepelster.myadmin.db.util.EntityVOMapper;

@Transactional(propagation = Propagation.REQUIRED)
public class BaseVODAO
{
	private List<IVODAOCallback> voDAOCallbacks = new ArrayList<IVODAOCallback>();

	@Autowired(required = false)
	private List<IVODaoDecorator> voDaoDecorators = new ArrayList<IVODaoDecorator>();

	@Autowired
	private BaseDAO baseDAO;

	public long aggregate(GenericFilterVO<?> genericFilterVO, String field, AGGREGATE_TYPE aggregateType)
	{
		Class<? extends IBaseEntity> entityClass = DBUtil.convertVOClassToEntiyClass(BeanUtil.getVOClass(genericFilterVO.getVOClassName()));

		List<IConditionalExpression> conditionalExpressions = ConditionalExpressionVOUtil.getConditionalExpressions(genericFilterVO.getEntity().getCriteria());

		return this.baseDAO.aggregate(conditionalExpressions, entityClass, field, aggregateType);
	}

	private IBaseVO convertEntiyToVO(IBaseEntity entity, Map<Class<?>, List<String>> classLoadAssociations)
	{
		Class<?> entityClass = entity.getClass();

		return (IBaseVO) CopyBean.getInstance().copyObject(entity, EntityVOMapper.getInstance().getMappedClass(entityClass), classLoadAssociations);
	}

	public <T extends IBaseVO> T create(T vo)
	{
		IBaseEntity entity = (IBaseEntity) CopyBean.getInstance().copyObject(vo, EntityVOMapper.getInstance().getMappedClass(vo.getClass()));
		IBaseEntity res = this.baseDAO.create(entity);

		T result = (T) convertEntiyToVO(res, null);

		decorateVO(result);

		callOnAdd(result);

		return result;
	}

	private void decorateVO(IBaseVO vo)
	{
		for (IVODaoDecorator voDaoDecorator : this.voDaoDecorators)
		{
			if (voDaoDecorator.supports(vo.getClass()))
			{
				voDaoDecorator.decorateVO(vo);
			}
		}
	}

	public <T extends IBaseVO> void delete(T vo)
	{
		IBaseEntity entity = (IBaseEntity) CopyBean.getInstance().copyObject(vo, EntityVOMapper.getInstance().getMappedClass(vo.getClass()));

		this.baseDAO.delete(entity);

		callOnDelete(vo);

	}

	public void deleteAll(Class<? extends IBaseVO> voClass)
	{
		Class<? extends IBaseEntity> entityClass = (Class<? extends IBaseEntity>) EntityVOMapper.getInstance().getMappedClass(voClass);
		this.baseDAO.deleteAll(entityClass);

		callOnDeleteAll(voClass);
	}

	public <T extends IBaseVO> List<T> filter(GenericFilterVO<?> genericFilterVO)
	{

		SelectQuery selectQuery = DBUtil.filter2SelectQuery(genericFilterVO);

		Map<Class<?>, List<String>> classLoadAssociations = DBUtil.filter2Associations(genericFilterVO);

		Class<?> voClass = BeanUtil.getVOClass(genericFilterVO.getVOClassName());
		DBUtil.addFirstLevelIBaseVOAttributes(voClass, classLoadAssociations);

		List<T> result = new ArrayList<T>();
		for (IBaseEntity baseEntity : this.baseDAO.filter(selectQuery, genericFilterVO.getFirstResult(), genericFilterVO.getMaxResults()))
		{
			T vo = (T) convertEntiyToVO(baseEntity, classLoadAssociations);
			decorateVO(vo);

			result.add(vo);
		}

		return result;
	}

	public <T extends IBaseVO> List<T> getAll(Class<T> voClass)
	{
		Class<? extends IBaseEntity> entityClass = DBUtil.convertVOClassToEntiyClass(voClass);

		List<T> result = new ArrayList<T>();
		for (IBaseEntity baseEntity : this.baseDAO.getAll(entityClass))
		{
			result.add((T) convertEntiyToVO(baseEntity, new HashMap<Class<?>, List<String>>()));
		}

		return result;
	}

	public long getCount(Class<? extends IBaseVO> voClass)
	{
		Class<? extends IBaseEntity> entityClass = DBUtil.convertVOClassToEntiyClass(voClass);

		return this.baseDAO.getCount(entityClass, new ArrayList<IConditionalExpression>());
	}

	public long getCount(GenericFilterVO<?> genericFilterVO)
	{
		Class<? extends IBaseEntity> entityClass = DBUtil.convertVOClassToEntiyClass(BeanUtil.getVOClass(genericFilterVO.getVOClassName()));

		return this.baseDAO.getCount(entityClass, ConditionalExpressionVOUtil.getConditionalExpressions(genericFilterVO.getEntity().getCriteria()));
	}

	public <T extends IBaseVO> T read(GenericFilterVO<T> genericFilterVO)
	{
		List<T> result = filter(genericFilterVO);

		if (result.size() == 0)
		{
			return null;
		}
		else if (result.size() > 1)
		{
			throw new RuntimeException(String.format("expected a single result but got %d", result.size()));
		}
		else
		{
			return result.get(0);
		}
	}

	public <T extends IBaseVO> T read(long id, Class<T> voClass)
	{

		@SuppressWarnings("rawtypes")
		GenericFilterVO<T> genericFilter = new GenericFilterVO(voClass);
		genericFilter.addCriteria(IBaseVO.FIELD_ID, id);

		List<T> result = filter(genericFilter);

		if (result.size() == 1)
		{
			return result.get(0);
		}
		else
		{
			return null;
		}
	}

	public <T extends IBaseVO> T save(T vo)
	{
		Class<?> voClass = vo.getClass();

		IBaseEntity entity = (IBaseEntity) CopyBean.getInstance().copyObject(vo, EntityVOMapper.getInstance().getMappedClass(voClass));
		IBaseEntity entityResult = this.baseDAO.save(entity);

		T voResult = (T) convertEntiyToVO(entityResult, null);

		decorateVO(voResult);

		return voResult;
	}

	public void setBaseDAO(BaseDAO baseDAO)
	{
		this.baseDAO = baseDAO;
	}

	public List<IVODaoDecorator> getVoDaoDecorators()
	{
		return this.voDaoDecorators;
	}

	public List<IVODAOCallback> getVODAOCallbacks()
	{
		return this.voDAOCallbacks;
	}

	private <T extends IBaseVO> void callOnAdd(T vo)
	{
		for (IVODAOCallback voDAOCallback : this.voDAOCallbacks)
		{
			voDAOCallback.onAdd(vo);
		}
	}

	private <T extends IBaseVO> void callOnDelete(T vo)
	{
		for (IVODAOCallback voDAOCallback : this.voDAOCallbacks)
		{
			voDAOCallback.onDelete(vo);
		}
	}

	private void callOnDeleteAll(Class<? extends IBaseVO> voClass)
	{
		for (IVODAOCallback voDAOCallback : this.voDAOCallbacks)
		{
			voDAOCallback.onDeleteAll(voClass);
		}
	}

}
