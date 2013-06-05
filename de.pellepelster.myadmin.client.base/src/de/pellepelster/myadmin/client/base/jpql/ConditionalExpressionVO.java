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
package de.pellepelster.myadmin.client.base.jpql;

import java.io.Serializable;
import java.sql.Timestamp;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.expressions.EntityExpressionObjectVO;
import de.pellepelster.myadmin.client.base.jpql.expressions.LongExpressionObjectVO;
import de.pellepelster.myadmin.client.base.jpql.expressions.NamedParameterExpressionObjectVO;
import de.pellepelster.myadmin.client.base.jpql.expressions.NullExpressionObjectVO;
import de.pellepelster.myadmin.client.base.jpql.expressions.StringExpressionObjectVO;
import de.pellepelster.myadmin.client.base.jpql.expressions.TimestampExpressionObjectVO;

public class ConditionalExpressionVO implements Serializable, IConditionalExpressionVO
{
	private static final long serialVersionUID = 8551952746847094312L;

	private IExpressionObjectVO expressionObject1;

	private IExpressionObjectVO expressionObject2;

	private LogicalOperatorVO logicalOperator;

	private RelationalOperator relationalOperator;

	public ConditionalExpressionVO()
	{
	}

	public ConditionalExpressionVO(String field, Object value)
	{
		this(field, value, RelationalOperator.EQUAL);
	}

	public ConditionalExpressionVO(String field, Object value, RelationalOperator relop)
	{
		this.expressionObject1 = new EntityExpressionObjectVO(field);
		this.logicalOperator = LogicalOperatorVO.AND;

		if (value == null)
		{
			this.expressionObject2 = new NullExpressionObjectVO();
			this.relationalOperator = RelationalOperator.IS;
		}
		else if (value instanceof IBaseVO)
		{
			this.expressionObject2 = new NamedParameterExpressionObjectVO(field, (IBaseVO) value);
			this.relationalOperator = relop;
		}
		else if (value instanceof String)
		{
			this.expressionObject2 = new StringExpressionObjectVO((String) value);
			this.relationalOperator = relop;
		}
		else if (value instanceof Integer)
		{
			this.expressionObject2 = new IntegerExpressionObjectVO((Integer) value);
			this.relationalOperator = relop;
		}
		else if (value instanceof Timestamp)
		{
			this.expressionObject2 = new TimestampExpressionObjectVO((Timestamp) value);
			this.relationalOperator = relop;
		}
		else if (value instanceof Long)
		{
			this.expressionObject2 = new LongExpressionObjectVO((Long) value);
			this.relationalOperator = relop;
		}
		else
		{
			throw new RuntimeException("unsupported value object type '" + value.getClass().getName() + "'");
		}
	}

	@Override
	public IExpressionObjectVO getExpressionObject1()
	{
		return this.expressionObject1;
	}

	@Override
	public IExpressionObjectVO getExpressionObject2()
	{
		return this.expressionObject2;
	}

	@Override
	public LogicalOperatorVO getLogicalOperator()
	{
		return this.logicalOperator;
	}

	@Override
	public RelationalOperator getRelationalOperator()
	{
		return this.relationalOperator;
	}

	public void setExpressionObject1(IExpressionObjectVO expressionObject1)
	{
		this.expressionObject1 = expressionObject1;
	}

	public void setExpressionObject2(IExpressionObjectVO expressionObject2)
	{
		this.expressionObject2 = expressionObject2;
	}

	public void setLogicalOperator(LogicalOperatorVO logicalOperator)
	{
		this.logicalOperator = logicalOperator;
	}

	public void setRelationalOperator(RelationalOperator relationalOperator)
	{
		this.relationalOperator = relationalOperator;
	}

	@Override
	public String toString()
	{
		return this.expressionObject1.toString() + ' ' + this.relationalOperator.toString() + ' ' + this.expressionObject2.toString();
	}

}
