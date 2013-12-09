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

import java.util.Arrays;
import java.util.Iterator;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public class AttributeDescriptorIterator<T> implements Iterable<IAttributeDescriptor<T>>, Iterator<IAttributeDescriptor<T>>
{
	private final Iterator<IAttributeDescriptor<?>> iterator;

	public AttributeDescriptorIterator(IBaseVO vo, final Class<T> attributeTypeClass)
	{

		this.iterator = Iterables.filter(Arrays.asList(BeanUtil.getAttributeDescriptors(vo.getClass())), new Predicate<IAttributeDescriptor>()
		{

			@Override
			public boolean apply(IAttributeDescriptor input)
			{
				return attributeTypeClass.isAssignableFrom(input.getAttributeType());
			}
		}).iterator();
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasNext()
	{
		return this.iterator.hasNext();
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<IAttributeDescriptor<T>> iterator()
	{
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public IAttributeDescriptor<T> next()
	{
		return (IAttributeDescriptor<T>) this.iterator.next();
	}

	/** {@inheritDoc} */
	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
