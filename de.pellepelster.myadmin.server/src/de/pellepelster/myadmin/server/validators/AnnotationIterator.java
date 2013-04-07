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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.db.util.BeanUtil;

public class AnnotationIterator implements Iterable<IAttributeDescriptor<?>>, Iterator<IAttributeDescriptor<?>>
{
	private List<IAttributeDescriptor<?>> attributeDescriptors = new ArrayList<IAttributeDescriptor<?>>();

	public AnnotationIterator(Class<? extends IBaseVO> voClass, @SuppressWarnings("rawtypes") Class annotationClass)
	{
		this.attributeDescriptors = BeanUtil.getAnnotatedAttributes(voClass, annotationClass);
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasNext()
	{
		return this.attributeDescriptors.iterator().hasNext();
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<IAttributeDescriptor<?>> iterator()
	{
		return this.attributeDescriptors.iterator();
	}

	/** {@inheritDoc} */
	@Override
	public IAttributeDescriptor<?> next()
	{
		return this.attributeDescriptors.iterator().next();
	}

	/** {@inheritDoc} */
	@Override
	public void remove()
	{
		throw new RuntimeException("not implemented");
	}

}
