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
package de.pellepelster.myadmin.server.validators;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.db.util.BeanUtil;

public class AnnotationIterator implements Iterable<IAttributeDescriptor<?>>, Iterator<IAttributeDescriptor<?>>
{
	private static final Logger LOG = Logger.getLogger(AnnotationIterator.class);

	private final List<IAttributeDescriptor<?>> attributeDescriptors = new ArrayList<IAttributeDescriptor<?>>();

	@SuppressWarnings("unchecked")
	public AnnotationIterator(Class<? extends IBaseVO> voClass, @SuppressWarnings("rawtypes") Class annotationClass)
	{
		for (IAttributeDescriptor<?> attributeDescriptor : BeanUtil.getAttributeDescriptors(voClass))
		{
			try
			{
				Field field = voClass.getDeclaredField(attributeDescriptor.getAttributeName());

				if (annotationClass == null || field.getAnnotation(annotationClass) != null)
				{
					attributeDescriptors.add(attributeDescriptor);
				}
			}
			catch (Exception e)
			{
				LOG.error(
						String.format("could not get PropertyDescriptor for field '%s' on class '%s'", attributeDescriptor.getAttributeName(),
								voClass.getName()), e);
			}
		}

	}

	/** {@inheritDoc} */
	@Override
	public boolean hasNext()
	{
		return attributeDescriptors.iterator().hasNext();
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<IAttributeDescriptor<?>> iterator()
	{
		return attributeDescriptors.iterator();
	}

	/** {@inheritDoc} */
	@Override
	public IAttributeDescriptor<?> next()
	{
		return attributeDescriptors.iterator().next();
	}

	/** {@inheritDoc} */
	@Override
	public void remove()
	{
		throw new RuntimeException("not implemented");
	}

}
