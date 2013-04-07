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

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import de.pellepelster.myadmin.client.base.jpql.LogicalOperatorVO;
import de.pellepelster.myadmin.db.IBaseClientEntity;
import de.pellepelster.myadmin.db.IBaseDAO;
import de.pellepelster.myadmin.db.IBaseEntity;
import de.pellepelster.myadmin.db.IBaseInfoEntity;
import de.pellepelster.myadmin.db.IUser;
import de.pellepelster.myadmin.db.jpql.AggregateQuery;
import de.pellepelster.myadmin.db.jpql.AggregateQuery.AGGREGATE_TYPE;
import de.pellepelster.myadmin.db.jpql.CountQuery;
import de.pellepelster.myadmin.db.jpql.SelectQuery;
import de.pellepelster.myadmin.db.jpql.expression.ConditionalExpression;
import de.pellepelster.myadmin.db.jpql.expression.IConditionalExpression;
import de.pellepelster.myadmin.db.jpql.expression.NamedParameterExpressionObject;

/**
 * Implementation for {@link IBaseDAO}
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
@Transactional()
public class BaseDAOImpl implements IBaseDAO
{

	private static Logger LOG = Logger.getLogger(BaseDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	private final String UNKNOWN_USERNAME = "<unknown>";

	/** {@inheritDoc} */
	@Override
	public <T extends IBaseEntity> long aggregate(List<IConditionalExpression> conditionalExpressions, Class<T> entityClass, String field,
			AGGREGATE_TYPE aggregateType)
	{

		AggregateQuery aggregateQuery = new AggregateQuery(entityClass, field, aggregateType);
		String jpql = aggregateQuery.getJPQL();
		LOG.debug(String.format("jpql '%s'", jpql));

		Query query = this.entityManager.createQuery(jpql);

		long result = (Long) query.getSingleResult();

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public <T extends IBaseEntity> T create(T entity)
	{
		if (entity instanceof IBaseClientEntity)
		{
			populateClients(((IBaseClientEntity) entity), new ArrayList<IBaseClientEntity>());

			LOG.debug(String.format("creating entity '%s' for client '%s'", entity.getClass().getName(), getCurrentUser().getClient()));
		}
		else
		{
			LOG.debug(String.format("creating entity '%s'", entity.getClass().getName()));
		}

		if (entity instanceof IBaseInfoEntity)
		{
			IBaseInfoEntity infoEntity = (IBaseInfoEntity) entity;

			if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null)
			{
				infoEntity.setUpdateUser(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
				infoEntity.setCreateUser(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
			}

			Date now = new Date();
			infoEntity.setCreateDate(now);
			infoEntity.setUpdateDate(now);
		}

		T result = mergeRecursive(entity, new HashMap<IBaseEntity, IBaseEntity>());

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public <T extends IBaseEntity> void delete(T entity)
	{
		LOG.debug(String.format("deleting entity '%s' with id '%d'", entity.getClass().getName(), entity.getId()));

		T entityToDelete = this.entityManager.merge(entity);
		this.entityManager.remove(entityToDelete);
	}

	/** {@inheritDoc} */
	@Override
	public void deleteAll(Class<? extends IBaseEntity> entityClass)
	{
		LOG.debug(String.format("deleting all '%s' entities", entityClass.getName()));

		for (Object entity : getAll(entityClass))
		{
			this.entityManager.remove(entity);
		}

		// @see https://github.com/pellepelster/myadmin/issues/8

		// if (BeanUtil.hasAnnotatedAttribute1(entityClass,
		// ElementCollection.class))
		// {
		// }
		// else
		// {
		// DeleteQuery deleteQuery = new DeleteQuery(entityClass);
		//
		// if (IBaseClientEntity.class.isAssignableFrom(entityClass))
		// {
		// deleteQuery.addWhereCondition(getClientConditionalExpression(entityClass));
		// }
		//
		// Query query = this.entityManager.createQuery(deleteQuery.getJPQL());
		// for (NamedParameterExpressionObject namedParameter :
		// deleteQuery.getNamedParameters())
		// {
		// query.setParameter(namedParameter.getName(),
		// namedParameter.getObject());
		// }
		//
		// query.executeUpdate();
		// }
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IBaseEntity> List<T> filter(SelectQuery selectQuery, int start, int limit)
	{

		Class<? extends IBaseEntity> entityClass = selectQuery.getEntityClass();

		// add all first level associations
		for (Field field : entityClass.getDeclaredFields())
		{
			Annotation[] annotations = field.getAnnotations();

			for (Annotation annotation : annotations)
			{
				Class<?> annotationType = annotation.annotationType();
				if (OneToOne.class.isAssignableFrom(annotationType) || ManyToOne.class.isAssignableFrom(annotationType) && !field.getType().equals(entityClass))
				{
					selectQuery.addJoin(field.getName());

				}
				else if (OneToMany.class.isAssignableFrom(annotationType) || ManyToMany.class.isAssignableFrom(annotationType))
				{
					selectQuery.removeJoin(field.getName());
				}
			}
		}

		// add client condition if needed
		if (IBaseClientEntity.class.isAssignableFrom(entityClass))
		{
			LOG.debug(String.format("filtering entity '%s' for client '%s'", entityClass.getName(), getCurrentUser().getClient()));
			selectQuery.addWhereCondition(getClientConditionalExpression(entityClass));
		}
		else
		{
			LOG.debug(String.format("filtering entity '%s'", entityClass.getName()));
		}

		String jpql = selectQuery.getJPQL();
		LOG.debug(String.format("jpql '%s'", jpql));

		List<T> result = new ArrayList<T>();

		Query query = this.entityManager.createQuery(jpql);
		for (NamedParameterExpressionObject namedParameter : selectQuery.getNamedParameters())
		{
			query.setParameter(namedParameter.getName(), namedParameter.getObject());
		}

		if (start > 0)
		{

			query.setFirstResult(start);

			if (limit > 0)
			{
				query.setMaxResults(limit);
			}
		}

		result = query.getResultList();
		LOG.debug(String.format("filtering entity '%s' returned %d results", selectQuery.getEntityClass().getName(), result.size()));

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public <T extends IBaseEntity> List<T> getAll(Class<T> entityClass)
	{
		return filter(new SelectQuery(entityClass), -1, -1);
	}

	private IConditionalExpression getClientConditionalExpression(Class<? extends IBaseEntity> entityClass)
	{
		return new ConditionalExpression("client", getCurrentUser().getClient());
	}

	/** {@inheritDoc} */
	@Override
	public long getCount(Class<? extends IBaseEntity> entityClass, List<IConditionalExpression> conditionalExpressions)
	{
		CountQuery countQuery = new CountQuery(entityClass, LogicalOperatorVO.AND);

		if (IBaseClientEntity.class.isAssignableFrom(entityClass))
		{
			conditionalExpressions.add(getClientConditionalExpression(entityClass));

			LOG.debug(String.format("counting '%s' for client '%s'", entityClass.getName(), getCurrentUser().getClient()));
		}
		else
		{
			LOG.debug(String.format("counting '%s'", entityClass.getName()));

		}

		countQuery.addWhereConditions(conditionalExpressions);

		String queryJPQL = countQuery.getJPQL();
		Query query = this.entityManager.createQuery(queryJPQL);
		for (NamedParameterExpressionObject namedParameter : countQuery.getNamedParameters())
		{
			query.setParameter(namedParameter.getName(), namedParameter.getObject());
		}

		Long result = (Long) query.getSingleResult();

		LOG.debug(String.format("count for '%s' is %d", entityClass.getName(), result));

		return result;
	}

	private IUser getCurrentUser()
	{

		try
		{
			return (IUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		}
		catch (Exception e)
		{
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	private void populateClients(IBaseClientEntity clientEntity, List<IBaseClientEntity> visited)
	{
		try
		{
			clientEntity.setClient(getCurrentUser().getClient());
			visited.add(clientEntity);

			for (Map.Entry<String, Object> entry : ((Map<String, Object>) PropertyUtils.describe(clientEntity)).entrySet())
			{

				String propertyName = entry.getKey();
				PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(clientEntity, propertyName);

				if (propertyDescriptor != null)
				{
					Object attribute = PropertyUtils.getSimpleProperty(clientEntity, propertyName);

					if (attribute != null && IBaseClientEntity.class.isAssignableFrom(attribute.getClass()))
					{
						if (!visited.contains(attribute))
						{
							populateClients((IBaseClientEntity) attribute, visited);
						}
					}

					if (attribute != null && List.class.isAssignableFrom(attribute.getClass()))
					{
						List<?> list = (List<?>) attribute;

						for (Object listElement : list)
						{
							if (IBaseClientEntity.class.isAssignableFrom(listElement.getClass()))
							{
								if (!visited.contains(attribute))
								{
									populateClients((IBaseClientEntity) listElement, visited);
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException("error setting client", e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public <T extends IBaseEntity> T read(long id, Class<T> entityClass)
	{

		T entity = this.entityManager.find(entityClass, id);

		if (entity instanceof IBaseClientEntity)
		{

			IUser user = getCurrentUser();

			LOG.debug(String.format("retrieving entity '%s' with id %d for client '%s'", entityClass.getName(), id, getCurrentUser().getClient()));

			if (((IBaseClientEntity) entity).getClient().getId() == user.getClient().getId())
			{
				return entity;
			}
			else
			{
				throw new AccessDeniedException(String.format("entity '%s' not allowed for client '%s'", entity, user.getClient()));
			}
		}
		else
		{
			LOG.debug(String.format("retrieving entity '%s' with id %d", entityClass.getName(), id));

			return entity;
		}
	}

	/** {@inheritDoc} */
	@Override
	public <T extends IBaseEntity> T save(T entity)
	{

		if (entity instanceof IBaseClientEntity)
		{
			((IBaseClientEntity) entity).setClient(getCurrentUser().getClient());

			LOG.debug(String.format("saving '%s' for client '%s'", entity.getClass(), getCurrentUser().getClient()));
		}
		else
		{
			// throw new AccessDeniedException(String.format(
			// "entity '%s' not allowed for client '%s'", entity,
			// getCurrentUser().getClient()));
		}

		if (entity instanceof IBaseInfoEntity)
		{
			IBaseInfoEntity infoEntity = (IBaseInfoEntity) entity;

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal != null)
			{
				infoEntity.setUpdateUser(principal.toString());
			}
			else
			{
				infoEntity.setUpdateUser(this.UNKNOWN_USERNAME);
			}

			infoEntity.setUpdateDate(new Date());
		}

		T result = mergeRecursive(entity, new HashMap<IBaseEntity, IBaseEntity>());

		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T extends IBaseEntity> T mergeRecursive(T entity, Map<IBaseEntity, IBaseEntity> visited)
	{
		try
		{
			T mergedEntity = this.entityManager.merge(entity);
			visited.put(entity, mergedEntity);

			for (Map.Entry<String, Object> entry : ((Map<String, Object>) PropertyUtils.describe(mergedEntity)).entrySet())
			{

				String propertyName = entry.getKey();
				PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(mergedEntity, propertyName);

				if (propertyDescriptor != null)
				{
					Object attribute = PropertyUtils.getSimpleProperty(entity, propertyName);

					if (attribute != null && IBaseEntity.class.isAssignableFrom(attribute.getClass()))
					{
						if (visited.containsKey(attribute))
						{
							PropertyUtils.setProperty(mergedEntity, propertyName, visited.get(attribute));
						}
						else
						{
							PropertyUtils.setProperty(mergedEntity, propertyName, mergeRecursive((IBaseEntity) attribute, visited));
						}
					}

					if (attribute != null && List.class.isAssignableFrom(attribute.getClass()))
					{
						List list = (List) attribute;
						List mergedList = (List) PropertyUtils.getSimpleProperty(mergedEntity, propertyName);

						for (int i = 0; i < list.size(); i++)
						{
							Object listElement = list.get(i);

							if (IBaseEntity.class.isAssignableFrom(listElement.getClass()))
							{
								if (visited.containsKey(listElement))
								{
									mergedList.set(i, visited.get(listElement));
								}
								else
								{
									mergedList.set(i, mergeRecursive((IBaseEntity) listElement, visited));
								}
							}
						}
					}
				}
			}

			return mergedEntity;
		}
		catch (Exception e)
		{
			throw new RuntimeException(String.format("error saving entity '%s'", entity.toString()), e);
		}
	}

	/**
	 * @param entityManager
	 */
	public void setEntityManager(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

}
