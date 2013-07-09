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
package de.pellepelster.myadmin.client.core.query;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;

public abstract class BaseGenericFilterBuilder<T extends IBaseVO, F>
{
	private GenericFilterVO<T> genericFilterVO;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected BaseGenericFilterBuilder(String voClassName)
	{
		super();
		this.genericFilterVO = new GenericFilterVO(voClassName);
	}

	public BaseGenericFilterBuilder<T, F> addCriteria(IAttributeDescriptor<?> attributeDescriptor, Object value)
	{
		this.genericFilterVO.addCriteria(attributeDescriptor, value);

		return this;
	}

	protected abstract F getFilterBuilder();

	public BaseGenericFilterBuilder<T, F> addAssociation(IAttributeDescriptor<?> attributeDescriptor)
	{
		this.genericFilterVO.addAssociation(attributeDescriptor);

		return this;
	}

	public GenericFilterVO<T> getGenericFilter()
	{
		return this.genericFilterVO;
	}
}
