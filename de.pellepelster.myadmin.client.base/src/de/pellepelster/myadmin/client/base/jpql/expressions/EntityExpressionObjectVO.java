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
package de.pellepelster.myadmin.client.base.jpql.expressions;

import java.io.Serializable;

import de.pellepelster.myadmin.client.base.jpql.IExpressionObjectVO;

public class EntityExpressionObjectVO implements Serializable, IExpressionObjectVO
{
	private static final long serialVersionUID = 6136700255909180860L;
	private String field;

	public EntityExpressionObjectVO()
	{
	}

	public EntityExpressionObjectVO(String field)
	{
		this.field = field;
	}

	public String getField()
	{
		return field;
	}

	/** {@inheritDoc} */
	@Override
	public Object getValue()
	{
		return field;
	}

}
