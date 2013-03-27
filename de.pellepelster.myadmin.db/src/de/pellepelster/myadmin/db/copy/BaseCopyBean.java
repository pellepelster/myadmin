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
package de.pellepelster.myadmin.db.copy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;

public abstract class BaseCopyBean
{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getAnnotation(Class<?> clazz, String fieldName, Class annotationClass)
	{
		Field field = getDeclaredField(clazz, fieldName);

		if (field != null)
		{
			return field.getAnnotation(annotationClass);
		}
		{
			return null;
		}
	}

	public static Field getDeclaredField(Class<?> clazz, String fieldName)
	{
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields)
		{
			if (field.getName().equals(fieldName))
			{
				return field;
			}
		}

		return null;
	}

	public static boolean hasAnnotation(Class<?> clazz, String fieldName, Class<?> annotationClass)
	{
		return getAnnotation(clazz, fieldName, annotationClass) != null;
	}

	private final List<IFieldCopyHandler> fieldCopyHandlers = new ArrayList<IFieldCopyHandler>();

	public Object copyObject(Object sourceObject, Class<?> destClass)
	{
		return copyObject(sourceObject, destClass, new HashMap<Object, Object>(), null, new ArrayList<String>());
	}

	public Object copyObject(Object sourceObject, Class<?> destClass, String[] attributesToOmit)
	{
		return copyObject(sourceObject, destClass, new HashMap<Object, Object>(), null, Arrays.asList(attributesToOmit));
	}

	public Object copyObject(Object sourceObject, Class<?> destClass, Map<Class<?>, List<String>> classLoadAssociations)
	{
		return copyObject(sourceObject, destClass, new HashMap<Object, Object>(), classLoadAssociations, new ArrayList<String>());
	}

	public Object copyObject(Object sourceObject)
	{
		return copyObject(sourceObject, getMappedTargetType(sourceObject.getClass()), new HashMap<Object, Object>(), null, new ArrayList<String>());
	}

	public void addFieldCopyHandler(IFieldCopyHandler fieldCopyHandler)
	{
		fieldCopyHandlers.add(fieldCopyHandler);
	}

	private Object copyObject(Object sourceObject, Class<?> destClass, Map<Object, Object> visited, Map<Class<?>, List<String>> classLoadAssociations, List<String> attributesToOmit)
	{
		List<String> loadAssociations = null;

		if (classLoadAssociations != null)
		{

			loadAssociations = new ArrayList<String>();

			if (classLoadAssociations.containsKey(sourceObject.getClass()))
			{
				loadAssociations = classLoadAssociations.get(sourceObject.getClass());
			}

			if (classLoadAssociations.containsKey(destClass))
			{
				loadAssociations = classLoadAssociations.get(destClass);
			}
		}

		if (sourceObject == null)
		{
			return null;
		}

		Object targetObject;
		try
		{
			targetObject = ConstructorUtils.invokeConstructor(destClass, new Object[] {});
			visited.put(sourceObject, targetObject);

			for (FieldDescriptor fieldDescriptor : new FieldIterator(sourceObject, targetObject))
			{

				if (fieldDescriptor.sourceHasReadMethod() && fieldDescriptor.targetHasWriteMethod())
				{

					if (fieldDescriptor.getSourceValue() == null || attributesToOmit.contains(fieldDescriptor.getFieldName()))
					{
						continue;
					}
					
					for (IFieldCopyHandler fieldCopyHandler : fieldCopyHandlers)
					{
						if (fieldCopyHandler.check(fieldDescriptor))
						{
							fieldCopyHandler.copy(fieldDescriptor, sourceObject, targetObject);
							
							break;
						}
					}


					// collection
					if (List.class.isAssignableFrom(fieldDescriptor.getSourceType()) && List.class.isAssignableFrom(fieldDescriptor.getTargetType()))
					{

						if (loadAssociations == null || loadAssociations.contains(fieldDescriptor.getFieldName()))
						{

							List<?> sourceList = (List<?>) fieldDescriptor.getSourceValue();
							@SuppressWarnings("unchecked")
							List<Object> targetList = (List<Object>) fieldDescriptor.getTargetValue();

							for (Object sourceListObject : sourceList)
							{
								if (visited.containsKey(sourceListObject))
								{
									targetList.add(visited.get(sourceListObject));
								}
								else if (sourceListObject == null)
								{
									// targetList.add(null);
								}
								else
								{
									Class<?> mappedItemType = getMappedTargetType(sourceListObject.getClass());

									if (mappedItemType != null)
									{
										targetList.add(copyObject(sourceListObject, mappedItemType, visited, classLoadAssociations, attributesToOmit));
									}
								}
							}

							PropertyUtils.setProperty(targetObject, fieldDescriptor.getFieldName(), targetList);
						}

						continue;
					}


					// source und target typ-mapped stimmen ueberein
					Class<?> mappedType = getMappedTargetType(fieldDescriptor.getTargetType());
					if (fieldDescriptor.getSourceType() == mappedType)
					{

						if (loadAssociations == null || loadAssociations.contains(fieldDescriptor.getFieldName()))
						{

							Object object;

							if (visited.containsKey(fieldDescriptor.getSourceValue()))
							{
								object = visited.get(fieldDescriptor.getSourceValue());
							}
							else
							{
								object = copyObject(fieldDescriptor.getSourceValue(), fieldDescriptor.getTargetType(), visited, classLoadAssociations, attributesToOmit);
								visited.put(fieldDescriptor.getSourceValue(), object);
							}

							PropertyUtils.setProperty(targetObject, fieldDescriptor.getFieldName(), object);
						}
						continue;
					}

				}
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		return targetObject;
	}

	protected Class<?> getMappedTargetType(Class<?> sourceType)
	{
		return null;
	}
	
	public Object copyObject(Object sourceObject, Map<Class<?>, List<String>> classLoadAssociations)
	{
		return copyObject(sourceObject, getMappedTargetType(sourceObject.getClass()), new HashMap<Object, Object>(),
				classLoadAssociations, new ArrayList<String>());
	}

}
