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

import org.springframework.transaction.annotation.Transactional;

import de.pellepelster.myadmin.db.IBaseVODAO;

/**
 * Implementation for {@link IBaseVODAO} that logs all changes for an entity
 * 
 * @author pelle
 * @version $Rev: 662 $, $Date: 2010-08-30 20:58:04 +0200 (Mon, 30 Aug 2010) $
 * 
 */
@Transactional
public class BaseChangeTrackingDAOImpl
{

	// private IBaseVODAO baseDAO;
	//
	// /** {@inheritDoc} */

	// public <T> T create(T object)
	// {
	// IBaseVO entity = (IBaseVO) getEntityObject(object);
	// trackChanges(entity, null);
	//
	// return baseDAO.create(object);
	// }
	//
	// /** {@inheritDoc} */

	// public <T> void delete(T object)
	// {
	// baseDAO.delete(object);
	// }
	//
	// /** {@inheritDoc} */

	// public void deleteAll(Class<?> clazz)
	// {
	// baseDAO.deleteAll(clazz);
	// }
	//
	// /** {@inheritDoc} */

	// public <T> List<T> filter(IConditionalExpression conditionalExpression,
	// Class<T> clazz)
	// {
	// return baseDAO.filter(conditionalExpression, clazz);
	// }
	//
	// /** {@inheritDoc} */

	// public <T> List<T> filter(List<IConditionalExpression>
	// conditionalExpressions, Class<T> clazz)
	// {
	// return baseDAO.filter(conditionalExpressions, clazz);
	// }
	//
	// /** {@inheritDoc} */

	// public <T> List<T> filter(List<IConditionalExpression>
	// conditionalExpressions, List<OrderClause> orderByClauses,
	// Class<T> clazz)
	// {
	// return baseDAO.filter(conditionalExpressions, orderByClauses, clazz);
	// }
	//

	// public <T> List<T> filter(List<IConditionalExpression>
	// conditionalExpressions, List<OrderClause> orderByClauses,
	// int start, int limit, Class<T> clazz)
	// {
	// return baseDAO.filter(conditionalExpressions, orderByClauses, start,
	// limit, clazz);
	// }
	//
	// /** {@inheritDoc} */

	// public <T> List<T> getAll(Class<T> clazz)
	// {
	// return baseDAO.getAll(clazz);
	// }
	//
	// /** {@inheritDoc} */

	// public long getCount(Class<?> clazz, List<IConditionalExpression>
	// conditionalExpressions)
	// {
	// return baseDAO.getCount(clazz, conditionalExpressions);
	// }
	//
	// /** {@inheritDoc} */

	// public <T> T read(long id, Class<T> clazz)
	// {
	// return baseDAO.read(id, clazz);
	// }
	//
	// /** {@inheritDoc} */

	// public <T> T save(T object)
	// {
	// IBaseVO newEntity = (IBaseVO) getEntityObject(object);
	// IBaseVO oldEntity = baseDAO.read(newEntity.getId(),
	// newEntity.getClass());
	// trackChanges(newEntity, oldEntity);
	//
	// return baseDAO.save(object);
	// }
	//
	// public void setBaseDAO(IBaseVODAO baseDAO)
	// {
	// this.baseDAO = baseDAO;
	// }
	//
	// private void trackChanges(IBaseVO newEntity, IBaseVO oldEntity)
	// {
	// EntityChange entityChange = new
	// EntityChange(newEntity.getClass().getName());
	// entityChange = baseDAO.create(entityChange);
	//
	// Field[] fields = newEntity.getClass().getDeclaredFields();
	// for (int j = 0; j < fields.length; j++)
	// {
	// Field field = fields[j];
	//
	// if (field.isAnnotationPresent(Column.class) &&
	// !field.isAnnotationPresent(Id.class))
	// {
	// try
	// {
	// EntityAttributeChange ec = null;
	// String newValue = PropertyUtils.getProperty(newEntity,
	// field.getName()).toString();
	//
	// if (oldEntity == null)
	// {
	// ec = new EntityAttributeChange(entityChange, field.getName(), newValue);
	// }
	// else
	// {
	// String oldValue = PropertyUtils.getProperty(oldEntity,
	// field.getName()).toString();
	// ec = new EntityAttributeChange(entityChange, field.getName(), oldValue,
	// newValue);
	// }
	//
	// baseDAO.create(ec);
	//
	// } catch (Exception e)
	// {
	// throw new RuntimeException(e);
	// }
	// }
	// }
	// }

}
