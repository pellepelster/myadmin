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

public final class GenericFilterFactory
{

	public static <T extends IBaseVO> GenericFilterVO<T> createGenericFilter(Class<T> voClass)
	{
		return createGenericFilter(voClass.getName());
	}

	public static <T extends IBaseVO> GenericFilterVO<T> createGenericFilter(Class<T> voClass, IAttributeDescriptor<?> attributeDescriptor, Object value)
	{
		return createGenericFilter(voClass, attributeDescriptor.getAttributeName(), value);

	}

	public static <T extends IBaseVO> GenericFilterVO<T> createGenericFilter(Class<T> voClass, String field, Object value)
	{
		return createGenericFilter(voClass.getName(), field, value);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends IBaseVO> GenericFilterVO<T> createGenericFilter(String voClassName)
	{
		GenericFilterVO<T> genericFilterVO = new GenericFilterVO(voClassName);

		return genericFilterVO;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends IBaseVO> GenericFilterVO<T> createGenericFilter(String className, String field, Object value)
	{

		GenericFilterVO<T> genericFilterVO = new GenericFilterVO(className);
		genericFilterVO.addCriteria(field, value);

		return genericFilterVO;
	}

	private GenericFilterFactory()
	{
	}

}
