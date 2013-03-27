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

public class VOExpressionObjectVO implements Serializable, IExpressionObjectVO
{

	private static final long serialVersionUID = 6136700255909180860L;
	private String field;
	private String voClass;

	public VOExpressionObjectVO()
	{
	}

	public VOExpressionObjectVO(String voClass, String field)
	{
		super();
		this.voClass = voClass;
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
		return voClass;
	}

	public String getVOClass()
	{
		return voClass;
	}

	public void setField(String field)
	{
		this.field = field;
	}

	public void setVOClass(String voClass)
	{
		this.voClass = voClass;
	}
}
