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

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.db.IBaseDAO;
import de.pellepelster.myadmin.db.IBaseEntity;
import de.pellepelster.myadmin.db.IBaseVODAO;
import de.pellepelster.myadmin.db.jpql.AggregateQuery.AGGREGATE_TYPE;
import de.pellepelster.myadmin.db.jpql.SelectQuery;
import de.pellepelster.myadmin.db.jpql.expression.IConditionalExpression;
import de.pellepelster.myadmin.db.util.BeanUtil;
import de.pellepelster.myadmin.db.util.CopyBean;
import de.pellepelster.myadmin.db.util.DBUtil;
import de.pellepelster.myadmin.db.util.EntityVOMapper;

/**
 * Implementation for {@link IBaseVODAO}
 * 
 * @author pelle
 * @version $Rev: 1031 $, $Date: 2011-04-27 17:42:03 +0200 (Wed, 27 Apr 2011) $
 * 
 */
public class BaseVODAOImpl implements IBaseVODAO
{

	@Autowired(required = false)
	private List<IVODaoDecorator> voDaoDecorators = new ArrayList<IVODaoDecorator>();

	@Autowired
	private IBaseDAO baseDAO;

	/** {@inheritDoc} */
	@Override
	public long aggregate(GenericFilterVO<?> genericFilterVO, String field, AGGREGATE_TYPE aggregateType)
	{
		Class<? extends IBaseEntity> entityClass = DBUtil.convertVOClassToEntiyClass(BeanUtil.getVOClass(genericFilterVO.getVOClassName()));

		List<IConditionalExpression> conditionalExpressions = ConditionalExpressionVOUtil.getConditionalExpressions(genericFilterVO.getEntity().getCriteria());

		return baseDAO.aggregate(conditionalExpressions, entityClass, field, aggregateType);
	}

	private IBaseVO convertEntiyToVO(IBaseEntity entity, Map<Class<?>, List<String>> classLoadAssociations)
	{

		Class<?> entityClass = entity.getClass();

		return (IBaseVO) CopyBean.getInstance().copyObject(entity, EntityVOMapper.getInstance().getMappedClass(entityClass), classLoadAssociations);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IBaseVO> T create(T vo)
	{

		IBaseEntity entity = (IBaseEntity) CopyBean.getInstance().copyObject(vo, EntityVOMapper.getInstance().getMappedClass(vo.getClass()));
		IBaseEntity res = baseDAO.create(entity);

		T result = (T) convertEntiyToVO(res, null);

		return result;
	}

	private void decorateVO(IBaseVO vo)
	{
		for (IVODaoDecorator voDaoDecorator : voDaoDecorators)
		{
			if (voDaoDecorator.supports(vo.getClass()))
			{
				voDaoDecorator.decorateVO(vo);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public <T extends IBaseVO> void delete(T vo)
	{
		IBaseEntity entity = (IBaseEntity) CopyBean.getInstance().copyObject(vo, EntityVOMapper.getInstance().getMappedClass(vo.getClass()));

		baseDAO.delete(entity);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteAll(Class<? extends IBaseVO> voClass)
	{
		Class<? extends IBaseEntity> entityClass = (Class<? extends IBaseEntity>) EntityVOMapper.getInstance().getMappedClass(voClass);
		baseDAO.deleteAll(entityClass);
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends IBaseVO> List<T> filter(GenericFilterVO<?> genericFilterVO)
	{

		SelectQuery selectQuery = DBUtil.filter2SelectQuery(genericFilterVO);

		Map<Class<?>, List<String>> classLoadAssociations = DBUtil.filter2Associations(genericFilterVO);

		Class<?> voClass = BeanUtil.getVOClass(genericFilterVO.getVOClassName());
		DBUtil.addFirstLevelIBaseVOAttributes(voClass, classLoadAssociations);

		List<T> result = new ArrayList<T>();
		for (IBaseEntity baseEntity : baseDAO.filter(selectQuery, genericFilterVO.getFirstResult(), genericFilterVO.getMaxResults()))
		{
			T vo = (T) convertEntiyToVO(baseEntity, classLoadAssociations);
			decorateVO(vo);

			result.add(vo);
		}

		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends IBaseVO> List<T> getAll(Class<T> voClass)
	{
		Class<? extends IBaseEntity> entityClass = DBUtil.convertVOClassToEntiyClass(voClass);

		List<T> result = new ArrayList<T>();
		for (IBaseEntity baseEntity : baseDAO.getAll(entityClass))
		{
			result.add((T) convertEntiyToVO(baseEntity, new HashMap<Class<?>, List<String>>()));
		}

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public long getCount(Class<? extends IBaseVO> voClass)
	{
		Class<? extends IBaseEntity> entityClass = DBUtil.convertVOClassToEntiyClass(voClass);

		return baseDAO.getCount(entityClass, new ArrayList<IConditionalExpression>());
	}

	/** {@inheritDoc} */
	@Override
	public long getCount(GenericFilterVO<?> genericFilterVO)
	{
		Class<? extends IBaseEntity> entityClass = DBUtil.convertVOClassToEntiyClass(BeanUtil.getVOClass(genericFilterVO.getVOClassName()));

		return baseDAO.getCount(entityClass, ConditionalExpressionVOUtil.getConditionalExpressions(genericFilterVO.getEntity().getCriteria()));
	}

	public List<IVODaoDecorator> getVoDaoDecorators()
	{
		return voDaoDecorators;
	}

	/** {@inheritDoc} */
	@Override
	public <T extends IBaseVO> T read(GenericFilterVO<T> genericFilterVO)
	{
		List<T> result = filter(genericFilterVO);

		if (result.size() != 1)
		{
			throw new RuntimeException(String.format("expected a single result but got %d", result.size()));
		}

		return result.get(0);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
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

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends IBaseVO> T save(T vo)
	{
		Class<?> voClass = vo.getClass();

		IBaseEntity entity = (IBaseEntity) CopyBean.getInstance().copyObject(vo, EntityVOMapper.getInstance().getMappedClass(voClass));

		IBaseEntity result = baseDAO.save(entity);
		return (T) convertEntiyToVO(result, null);
	}

	public void setBaseDAO(IBaseDAO baseDAO)
	{
		this.baseDAO = baseDAO;
	}

	public void setVoDaoDecorators(List<IVODaoDecorator> voDaoDecorators)
	{
		this.voDaoDecorators = voDaoDecorators;
	}

}
