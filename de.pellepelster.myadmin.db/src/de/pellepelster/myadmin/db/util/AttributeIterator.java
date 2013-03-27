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
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class AttributeIterator implements Iterable<Map.Entry<String, Object>>, Iterator<Map.Entry<String, Object>>
{

	private final Iterator<Map.Entry<String, Object>> iterator;

	private ArrayList<Map.Entry<String, Object>> attributes = new ArrayList<Map.Entry<String, Object>>();

	public AttributeIterator(Object o, Class<?>[] attributeClasses)
	{
		createAttributeList(o, attributeClasses);
		iterator = attributes.iterator();
	}

	@SuppressWarnings("unchecked")
	private void createAttributeList(Object o, Class<?>[] attributeClasses)
	{
		try
		{
			for (Map.Entry<String, Object> entry : ((Map<String, Object>) PropertyUtils.describe(o)).entrySet())
			{
				for(Class<?> clazz : attributeClasses)
				{
					if (entry.getValue() != null && clazz.isAssignableFrom(entry.getValue().getClass()))
					{
						attributes.add(entry);
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/** {@inheritDoc} */
	public boolean hasNext()
	{
		return iterator.hasNext();
	}

	/** {@inheritDoc} */
	public Iterator<Map.Entry<String, Object>> iterator()
	{
		return this;
	}

	/** {@inheritDoc} */
	public Map.Entry<String, Object> next()
	{
		return iterator.next();
	}

	/** {@inheritDoc} */
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
