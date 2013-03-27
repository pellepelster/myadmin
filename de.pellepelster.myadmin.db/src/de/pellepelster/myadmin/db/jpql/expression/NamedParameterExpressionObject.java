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
 * Implementation of {@link IExpressionObject} for a named parameter
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class NamedParameterExpressionObject implements IExpressionObject
{
	private final String name;
	private final Object object;

	/**
	 * Constructor for <code>NamedParameterExpressionObject</code>
	 * 
	 * @param name
	 * @param object
	 */
	public NamedParameterExpressionObject(String name, Object object)
	{
		this.name = name;
		this.object = object;
	}

	/** {@inheritDoc} */
	@Override
	public String getJPQL(IEntity parentEntity, RelationalOperator relationalOperator)
	{
		if (object == null)
		{
			return "IS NULL";
		}
		else
		{
			return ":" + name;
		}
	}

	/**
	 * Name of the named parameter
	 * 
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Content for the named parameter
	 * 
	 * @return
	 */
	public Object getObject()
	{
		return object;
	}

}
