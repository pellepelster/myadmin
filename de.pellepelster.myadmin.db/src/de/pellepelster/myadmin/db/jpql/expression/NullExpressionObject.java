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
 * Implementation if {@link IExpressionObject} for a null value
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class NullExpressionObject implements IExpressionObject
{

	/**
	 * Constructor for <code>NullExpressionObject</code>
	 */
	public NullExpressionObject()
	{
	}

	/** {@inheritDoc} */
	@Override
	public String getJPQL(IEntity parentEntity, RelationalOperator relationalOperator)
	{
		return "NULL";
	}
}
