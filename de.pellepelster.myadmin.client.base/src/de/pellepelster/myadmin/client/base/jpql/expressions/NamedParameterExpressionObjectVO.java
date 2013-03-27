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

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.IExpressionObjectVO;

public class NamedParameterExpressionObjectVO implements Serializable, IExpressionObjectVO
{
	private static final long serialVersionUID = 6136700255909680860L;

	private String name;
	private IBaseVO object;

	public NamedParameterExpressionObjectVO()
	{
	}

	public NamedParameterExpressionObjectVO(String name, IBaseVO object)
	{
		super();
		this.name = name;
		this.object = object;
	}

	public String getName()
	{
		return name;
	}

	public IBaseVO getObject()
	{
		return object;
	}

	@Override
	public Object getValue()
	{
		return object;
	}

}
