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

public class BooleanExpressionObjectVO implements Serializable, IExpressionObjectVO
{

	private static final long serialVersionUID = -7124999509584167909L;

	private Boolean value;

	public BooleanExpressionObjectVO()
	{
	}

	public BooleanExpressionObjectVO(Boolean value)
	{
		this.value = value;
	}

	@Override
	public Boolean getValue()
	{
		return this.value;
	}

	public void setValue(Boolean value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return this.value.toString();
	}

}
