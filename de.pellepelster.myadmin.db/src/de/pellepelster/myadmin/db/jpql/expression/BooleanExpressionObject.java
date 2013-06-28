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
 * Implementation of {@link IExpressionObject} representing a long
 * 
 * @author pelle
 * @version $Rev: 1 $, $Date: 2009-04-14 23:03:38 +0200 (Tue, 14 Apr 2009) $
 * 
 */
public class BooleanExpressionObject implements IExpressionObject
{
	private final Boolean value;

	/**
	 * Constructor for <code>LongExpressionObject</code>
	 * 
	 * @param integer
	 */
	public BooleanExpressionObject(Boolean value)
	{
		this.value = value;
	}

	/** {@inheritDoc} */
	@Override
	public String getJPQL(IEntity parentEntity, RelationalOperator relationalOperator)
	{
		return this.value.toString();
	}
}
