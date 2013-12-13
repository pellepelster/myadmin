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
package de.pellepelster.myadmin.db.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.pellepelster.gwt.commons.client.util.XPathUtil;
import de.pellepelster.myadmin.client.base.db.vos.ChangeTrackingArrayList;
import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.AssociationVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.jpql.OrderClauseVO;
import de.pellepelster.myadmin.db.IBaseEntity;
import de.pellepelster.myadmin.db.copy.FieldDescriptor;
import de.pellepelster.myadmin.db.copy.FieldIterator;
import de.pellepelster.myadmin.db.daos.ConditionalExpressionVOUtil;
import de.pellepelster.myadmin.db.jpql.Join;
import de.pellepelster.myadmin.db.jpql.Join.JOIN_TYPE;
import de.pellepelster.myadmin.db.jpql.SelectQuery;
import de.pellepelster.myadmin.db.jpql.expression.IConditionalExpression;

/**
 * Various DB utilities
 * 
 * @author pelle
 * 
 */
public final class DBUtil
{

	public static void addFirstLevelIBaseVOAttributes(Class<?> voClass, Map<Class<?>, Set<String>> associationsMap)
	{

		if (!associationsMap.containsKey(voClass))
		{
			associationsMap.put(voClass, new HashSet<String>());
		}

		Set<String> associations = associationsMap.get(voClass);

		IAttributeDescriptor<?>[] attributeDescriptors = BeanUtils.getAttributeDescriptors(voClass);

		for (IAttributeDescriptor<?> attributeDescriptor : attributeDescriptors)
		{
			if (IBaseVO.class.isAssignableFrom(attributeDescriptor.getAttributeType()))
			{
				associations.add(attributeDescriptor.getAttributeName());
			}
			else if (List.class.isAssignableFrom(attributeDescriptor.getAttributeType()))
			{
				associations.add(attributeDescriptor.getAttributeName());
			}
		}
	}

	private static void addJoins(Join parentJoin, AssociationVO parentAssociationVO)
	{
		for (AssociationVO associationVO : parentAssociationVO.getAssociations())
		{
			Join join = parentJoin.addJoin(JOIN_TYPE.LEFT, associationVO.getField());
			addJoins(join, associationVO);
		}
	}

	/**
	 * Converts an entity into its corresponding VO
	 * 
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends IBaseEntity> convertVOClassToEntiyClass(Class<? extends IBaseVO> voClass)
	{
		return (Class<? extends IBaseEntity>) EntityVOMapper.getInstance().getMappedClass(voClass);
	}

	public static Map<Class<?>, Set<String>> filter2Associations(GenericFilterVO<?> genericFilterVO)
	{

		Class<?> voClass = BeanUtils.getVOClass(genericFilterVO.getVOClassName());

		Map<Class<?>, Set<String>> associationsMap = new HashMap<Class<?>, Set<String>>();

		filter2AssociationsTree(voClass, genericFilterVO.getEntity().getAssociations(), associationsMap);

		return associationsMap;
	}

	private static void filter2AssociationsTree(Class<?> voClass, List<AssociationVO> associationVOs, Map<Class<?>, Set<String>> associationsMap)
	{

		if (!associationsMap.containsKey(voClass))
		{

			associationsMap.put(voClass, new HashSet<String>());
		}

		Set<String> associations = associationsMap.get(voClass);

		IAttributeDescriptor<?>[] attributeDescriptors = BeanUtils.getAttributeDescriptors(voClass);

		for (AssociationVO associationVO : associationVOs)
		{

			IAttributeDescriptor<?> attributeDescriptor = BeanUtils.getAttributeDescriptor(attributeDescriptors, associationVO.getField());
			if (attributeDescriptor == null)
			{
				throw new RuntimeException(String.format("class '%s' has no attribute descriptor for attribute '%s'", voClass.getName(),
						associationVO.getField()));
			}

			if (IBaseVO.class.isAssignableFrom(attributeDescriptor.getAttributeType())
					|| Collection.class.isAssignableFrom(attributeDescriptor.getAttributeType())
					|| Map.class.isAssignableFrom(attributeDescriptor.getAttributeType()))
			{
				associations.add(associationVO.getField());
			}

			if (!associationVO.getAssociations().isEmpty())
			{
				filter2AssociationsTree(
						attributeDescriptor.getListAttributeType() != null ? attributeDescriptor.getListAttributeType()
								: attributeDescriptor.getAttributeType(), associationVO.getAssociations(), associationsMap);
			}
		}

		associationsMap.put(voClass, associations);

	}

	public static SelectQuery filter2SelectQuery(GenericFilterVO<?> genericFilterVO)
	{

		Class<? extends IBaseEntity> entityClass = convertVOClassToEntiyClass(BeanUtils.getVOClass(genericFilterVO.getVOClassName()));
		SelectQuery selectQuery = new SelectQuery(entityClass, genericFilterVO.getLogicalOperator());

		List<IConditionalExpression> conditionalExpressions = ConditionalExpressionVOUtil.getConditionalExpressions(genericFilterVO.getEntity().getCriteria());

		for (IConditionalExpression conditionalExpression : conditionalExpressions)
		{
			selectQuery.addWhereCondition(conditionalExpression);
		}

		for (OrderClauseVO orderClauseVO : genericFilterVO.getEntity().getOrderBy())
		{
			selectQuery.addOrderBy(orderClauseVO.getField(), orderClauseVO.getOrderType());
		}

		for (AssociationVO associationVO : genericFilterVO.getEntity().getAssociations())
		{
			Join join = selectQuery.addJoin(associationVO.getField());
			addJoins(join, associationVO);
		}

		return selectQuery;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String mapToString(Map map)
	{

		String lineSeperator = System.getProperty("line.separator");

		if (map == null)
		{
			return "";
		}

		StringBuffer stringBuffer = new StringBuffer();

		Set<Map.Entry> s = map.entrySet();

		for (Map.Entry elem : s)
		{

			Object key = elem.getKey();
			Object value = elem.getValue();

			stringBuffer.append(key.toString());
			stringBuffer.append("=");
			stringBuffer.append(value == null ? "" : value.toString());
			stringBuffer.append(lineSeperator);

		}

		return stringBuffer.toString();
	}

	private DBUtil()
	{
	}

	public static Set<String> getDirtyPaths(IBaseVO vo)
	{

		Set<String> dirtyPaths = new HashSet<String>();

		createDirtyPaths(vo, new ArrayList<IBaseVO>(), "/", dirtyPaths);

		return dirtyPaths;
	}

	public static void createDirtyPaths(IBaseVO vo, List<IBaseVO> visited, String parentPath, Set<String> dirtyPaths)
	{
		if (visited.contains(vo))
		{
			return;
		}
		else
		{
			visited.add(vo);
		}

		if (vo.getChangeTracker().hasChanges())
		{
			dirtyPaths.add(parentPath);
		}

		for (FieldDescriptor fieldDescriptor : new FieldIterator(vo))
		{
			if (fieldDescriptor.getSourceValue() != null)
			{
				if (IBaseVO.class.isAssignableFrom(fieldDescriptor.getSourceType()))
				{
					createDirtyPaths((IBaseVO) fieldDescriptor.getSourceValue(), visited, XPathUtil.combine(parentPath, fieldDescriptor.getFieldName()),
							dirtyPaths);
					continue;
				}

				if (List.class.isAssignableFrom(fieldDescriptor.getSourceType()))
				{
					ChangeTrackingArrayList<IBaseVO> sourceList = (ChangeTrackingArrayList<IBaseVO>) fieldDescriptor.getSourceValue();

					String listPath = XPathUtil.combine(parentPath, fieldDescriptor.getFieldName());
					if (sourceList.hasChanges())
					{
						dirtyPaths.add(parentPath);
						dirtyPaths.add(listPath);
					}

					for (IBaseVO listVO : sourceList)
					{
						createDirtyPaths(listVO, visited, XPathUtil.addListIndentifier(listPath, listVO.getOid()), dirtyPaths);
					}

					continue;
				}
			}
		}

	}
}
