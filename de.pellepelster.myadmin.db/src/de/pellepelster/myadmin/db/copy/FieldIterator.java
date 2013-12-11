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

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;

import de.pellepelster.myadmin.db.util.CopyBean;

public class FieldIterator implements Iterable<FieldDescriptor>
{
	private final List<FieldDescriptor> properties = new ArrayList<FieldDescriptor>();

	private final List<String> attributesToOmit = new ArrayList<String>();
	
	public FieldIterator(Object sourceObject)
	{
		this(sourceObject, null);
	}

	public FieldIterator(Object sourceObject, Object targetObject)
	{
		attributesToOmit.add("class");
		
		try
		{
			for (Map.Entry<String, Object> entry : ((Map<String, Object>) PropertyUtils.describe(sourceObject)).entrySet())
			{
				String propertyName = entry.getKey();

				if (attributesToOmit.contains(propertyName) || CopyBean.hasAnnotation(sourceObject.getClass(), propertyName, Transient.class))
				{
					continue;
				}

				PropertyDescriptor sourcePropertyDescriptor = PropertyUtils.getPropertyDescriptor(sourceObject, propertyName);
				
				PropertyDescriptor targetPropertyDescriptor = null;
				if (targetObject != null)
				{
					targetPropertyDescriptor = PropertyUtils.getPropertyDescriptor(targetObject, propertyName);
				}

				Object sourceValue = null;

				if (sourcePropertyDescriptor != null)
				{
					sourceValue = PropertyUtils.getSimpleProperty(sourceObject, propertyName);
				}
				
				Object targetValue = null;
				if (targetPropertyDescriptor != null)
				{
						targetValue = PropertyUtils.getSimpleProperty(targetObject, propertyName);
				}

				FieldDescriptor fieldDescriptor = new FieldDescriptor(propertyName, sourcePropertyDescriptor, sourceValue, targetPropertyDescriptor, targetValue);
				
				properties.add(fieldDescriptor);
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

	/** {@inheritDoc} */
	@Override
	public Iterator<FieldDescriptor> iterator()
	{
		return properties.iterator();
	}

}
