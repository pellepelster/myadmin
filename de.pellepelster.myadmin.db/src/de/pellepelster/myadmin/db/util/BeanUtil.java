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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public class BeanUtil
{
	private static List<String> SPECIAL_FIELDS = Arrays.asList(new String[] { "class" });

	private static final Logger LOG = Logger.getLogger(BeanUtil.class);

	public static boolean hasAnnotatedAttribute1(Class<?> clazz, @SuppressWarnings("rawtypes") Class annotationClass)
	{
		return !getAnnotatedAttributes1(clazz, annotationClass).isEmpty();
	}

	@SuppressWarnings("unchecked")
	public static List<PropertyDescriptor> getAnnotatedAttributes1(Class<?> clazz, @SuppressWarnings("rawtypes") Class annotationClass)
	{
		List<PropertyDescriptor> propertyDescriptors = new ArrayList<PropertyDescriptor>();

		for (PropertyDescriptor propertyDescriptor : PropertyUtils.getPropertyDescriptors(clazz))
		{
			String propertyName = propertyDescriptor.getName();

			if (!SPECIAL_FIELDS.contains(propertyName))
			{

				try
				{
					Field field = clazz.getDeclaredField(propertyName);

					if (annotationClass == null || field.getAnnotation(annotationClass) != null)
					{
						propertyDescriptors.add(propertyDescriptor);
					}
				}
				catch (Exception e)
				{
					LOG.error(String.format("could not get property descriptors for field '%s' on class '%s'", propertyDescriptor.getName(), clazz.getName()),
							e);
				}
			}
		}

		return propertyDescriptors;
	}

	@SuppressWarnings("unchecked")
	public static List<IAttributeDescriptor<?>> getAnnotatedAttributes(Class<?> clazz, @SuppressWarnings("rawtypes") Class annotationClass)
	{
		List<IAttributeDescriptor<?>> attributeDescriptors = new ArrayList<IAttributeDescriptor<?>>();

		for (IAttributeDescriptor<?> attributeDescriptor : BeanUtil.getAttributeDescriptors(clazz))
		{
			try
			{
				Field field = clazz.getDeclaredField(attributeDescriptor.getAttributeName());

				if (annotationClass == null || field.getAnnotation(annotationClass) != null)
				{
					attributeDescriptors.add(attributeDescriptor);
				}
			}
			catch (Exception e)
			{
				LOG.error(String.format("could not get property descriptor for field '%s' on class '%s'", attributeDescriptor.getAttributeName(),
						clazz.getName()), e);
			}
		}

		return attributeDescriptors;
	}

	@SuppressWarnings("unchecked")
	public static Set<Class<? extends IBaseVO>> getDependentVOs(Class<?> clazz)
	{
		Set<Class<? extends IBaseVO>> voAttributeDescriptors = new HashSet<Class<? extends IBaseVO>>();

		for (IAttributeDescriptor<?> attributeDescriptor : getAttributeDescriptors(clazz))
		{
			if (IBaseVO.class.isAssignableFrom(attributeDescriptor.getAttributeType()))
			{
				voAttributeDescriptors.add((Class<? extends IBaseVO>) attributeDescriptor.getAttributeType());
			}

			if (attributeDescriptor.getListAttributeType() != null && IBaseVO.class.isAssignableFrom(attributeDescriptor.getListAttributeType()))
			{
				voAttributeDescriptors.add((Class<? extends IBaseVO>) attributeDescriptor.getListAttributeType());
			}
		}

		return voAttributeDescriptors;
	}

	public static IAttributeDescriptor<?> getAttributeDescriptor(IAttributeDescriptor<?>[] attributeDescriptors, String attributeName)
	{

		for (IAttributeDescriptor<?> attributeDescriptor : attributeDescriptors)
		{

			if (attributeDescriptor.getAttributeName().equals(attributeName))
			{
				return attributeDescriptor;
			}
		}

		return null;
	}

	public static IAttributeDescriptor<?>[] getAttributeDescriptors(Class<?> clazz)
	{

		try
		{
			Object o = MethodUtils.invokeStaticMethod(clazz, "getFieldDescriptors", null);
			return (IAttributeDescriptor<?>[]) o;
		}
		catch (Exception e)
		{
			throw new RuntimeException(String.format("error invoking method 'getFieldDescriptors' on class '%s'", clazz.getName()), e);
		}

	}

	@SuppressWarnings("unchecked")
	public static Class<? extends IBaseVO> getVOClass(String voClassName)
	{
		Class<? extends IBaseVO> voClass;
		try
		{
			voClass = (Class<? extends IBaseVO>) Class.forName(voClassName);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}

		return voClass;
	}

}
