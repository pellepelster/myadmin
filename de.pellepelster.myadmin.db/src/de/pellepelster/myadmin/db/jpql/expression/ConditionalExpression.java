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

import de.pellepelster.myadmin.client.base.jpql.LogicalOperatorVO;
import de.pellepelster.myadmin.client.base.jpql.RelationalOperator;
import de.pellepelster.myadmin.db.jpql.IEntity;

/**
 * Represents a conditional expression
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class ConditionalExpression implements IConditionalExpression
{

	private final IExpressionObject expressionObject1;
	private final IExpressionObject expressionObject2;
	private final LogicalOperatorVO logicalOperator;
	private RelationalOperator relationalOperator;

	/**
	 * Constructor for <code>ConditionalExpression</code>
	 * 
	 * @param logicalOperator
	 * @param expressionObject1
	 * @param relationalOperator
	 * @param expressionObject2
	 */
	public ConditionalExpression(LogicalOperatorVO logicalOperator, IExpressionObject expressionObject1, RelationalOperator relationalOperator,
			IExpressionObject expressionObject2)
	{
		this.logicalOperator = logicalOperator;
		this.expressionObject1 = expressionObject1;
		this.relationalOperator = relationalOperator;
		this.expressionObject2 = expressionObject2;
	}

	private void setRelationalOperatorIfNull(RelationalOperator relationalOperator)
	{
		if (this.relationalOperator == null)
		{
			this.relationalOperator = relationalOperator;
		}
	}

	public ConditionalExpression(String field, Object value, RelationalOperator relationalOperator)
	{
		this.logicalOperator = LogicalOperatorVO.AND;
		expressionObject1 = new EntityExpressionObject(field);

		if (relationalOperator != null)
		{
			this.relationalOperator = relationalOperator;
		}

		if (value == null)
		{
			expressionObject2 = new NullExpressionObject();
			setRelationalOperatorIfNull(RelationalOperator.IS);
		}
		else if (value instanceof String)
		{
			expressionObject2 = new StringExpressionObject((String) value);
			setRelationalOperatorIfNull(RelationalOperator.EQUAL);
		}
		else if (value instanceof Integer)
		{
			expressionObject2 = new IntegerExpressionObject((Integer) value);
			setRelationalOperatorIfNull(RelationalOperator.EQUAL);
		}
		else if (value instanceof Timestamp)
		{
			expressionObject2 = new TimestampExpressionObject((Timestamp) value);
			setRelationalOperatorIfNull(RelationalOperator.EQUAL);
		}
		else
		{
			expressionObject2 = new NamedParameterExpressionObject(field, value);
			setRelationalOperatorIfNull(RelationalOperator.EQUAL);
		}
	}

	public ConditionalExpression(String field, Object value)
	{
		this(field, value, null);
	}

	/** {@inheritDoc} */
	@Override
	public boolean containsNamedParameter()
	{

		if (expressionObject1 instanceof NamedParameterExpressionObject)
		{

			NamedParameterExpressionObject namedParamter = (NamedParameterExpressionObject) expressionObject1;

			if (namedParamter.getObject() == null)
			{
				return false;
			}
			else
			{
				return true;
			}

		}
		else if (expressionObject2 instanceof NamedParameterExpressionObject)
		{

			NamedParameterExpressionObject namedParamter = (NamedParameterExpressionObject) expressionObject2;

			if (namedParamter.getObject() == null)
			{
				return false;
			}
			else
			{
				return true;
			}

		}
		else
		{

			return false;

		}
	}

	/** {@inheritDoc} */
	@Override
	public String getJPQL(IEntity parentEntity)
	{
		return String.format("%s %s %s", expressionObject1.getJPQL(parentEntity, relationalOperator), relationalOperator.toString(),
				expressionObject2.getJPQL(parentEntity, relationalOperator));
	}

	/** {@inheritDoc} */
	@Override
	public LogicalOperatorVO getLogicalOperator()
	{
		return logicalOperator;
	}

	/** {@inheritDoc} */
	@Override
	public NamedParameterExpressionObject getNamedParameterObject()
	{

		if (expressionObject1 instanceof NamedParameterExpressionObject)
		{
			return (NamedParameterExpressionObject) expressionObject1;
		}

		if (expressionObject2 instanceof NamedParameterExpressionObject)
		{
			return (NamedParameterExpressionObject) expressionObject2;
		}

		throw new RuntimeException("expressions contains no named parameters");
	}

}
