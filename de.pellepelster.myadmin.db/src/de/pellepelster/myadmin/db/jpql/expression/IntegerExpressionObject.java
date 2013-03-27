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
package de.pellepelster.myadmin.db.jpql.expression;

import de.pellepelster.myadmin.client.base.jpql.RelationalOperator;
import de.pellepelster.myadmin.db.jpql.IEntity;

/**
 * Implementation of {@link IExpressionObject} representing an integer
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class IntegerExpressionObject implements IExpressionObject
{
	private final Integer integer;

	/**
	 * Constructor for <code>IntegerExpressionObject</code>
	 * 
	 * @param integer
	 */
	public IntegerExpressionObject(Integer integer)
	{
		this.integer = integer;
	}

	/** {@inheritDoc} */
	@Override
	public String getJPQL(IEntity parentEntity, RelationalOperator relationalOperator)
	{
		return integer.toString();
	}
}
