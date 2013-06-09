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
package de.pellepelster.myadmin.client.base.jpql;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public final class GenericFilterFactory<T extends IBaseVO>
{
	private GenericFilterVO<T> genericFilterVO;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericFilterFactory(String voClassName)
	{
		super();
		this.genericFilterVO = new GenericFilterVO(voClassName);
	}

	public static <T extends IBaseVO> GenericFilterFactory<T> createGenericFilter(Class<T> voClass)
	{
		return new GenericFilterFactory<T>(voClass.getName());
	}

	public static <T extends IBaseVO> GenericFilterFactory<T> createGenericFilter(String voClassName)
	{
		return new GenericFilterFactory<T>(voClassName);
	}

	public GenericFilterFactory<T> addCriteria(IAttributeDescriptor<?> attributeDescriptor, Object value)
	{
		this.genericFilterVO.addCriteria(attributeDescriptor, value);

		return this;
	}

	public GenericFilterFactory<T> addAssociation(IAttributeDescriptor<?> attributeDescriptor)
	{
		this.genericFilterVO.addAssociation(attributeDescriptor);

		return this;
	}

	public GenericFilterVO<T> getGenericFilter()
	{
		return this.genericFilterVO;
	}
}
