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

import de.pellepelster.myadmin.client.base.jpql.LogicalOperatorVO;
import de.pellepelster.myadmin.db.jpql.IEntity;

/**
 * Represents a conditional expression which can be used in a WHERE or HAVING
 * clause
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface IConditionalExpression
{
	/**
	 * Indicates whether this expression contains a named parameter
	 * 
	 * @return
	 */
	boolean containsNamedParameter();

	/**
	 * The JPQL for this expression
	 * 
	 * @return
	 */
	String getJPQL(IEntity parentEntity);

	/**
	 * The logical operator for this expression
	 * 
	 * @return
	 */
	LogicalOperatorVO getLogicalOperator();

	/**
	 * The {@link NamedParameterExpressionObject} for this expression if
	 * existent, otherwise null
	 * 
	 * @return
	 */
	NamedParameterExpressionObject getNamedParameterObject();

}
