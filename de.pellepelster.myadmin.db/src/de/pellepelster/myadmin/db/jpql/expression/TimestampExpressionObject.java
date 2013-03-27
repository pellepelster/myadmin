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

import java.sql.Timestamp;

import de.pellepelster.myadmin.client.base.jpql.RelationalOperator;
import de.pellepelster.myadmin.db.jpql.IEntity;

/**
 * Implementation of {@link IExpressionObject} representing a timestamp
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class TimestampExpressionObject implements IExpressionObject
{
	private final Timestamp timestamp;

	/**
	 * Constructor for <code>TimestampExpressionObject</code>
	 * 
	 * @param integer
	 */
	public TimestampExpressionObject(Timestamp timestamp)
	{
		this.timestamp = timestamp;
	}

	/** {@inheritDoc} */
	@Override
	public String getJPQL(IEntity parentEntity, RelationalOperator relationalOperator)
	{
		return "'" + timestamp.toString() + "'";
	}
}
