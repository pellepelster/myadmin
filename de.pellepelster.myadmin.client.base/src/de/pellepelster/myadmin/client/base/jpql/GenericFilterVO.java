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
import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.OrderClauseVO.ORDER_DIRECTION;

/**
 * Represents a filter for a VO
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class GenericFilterVO<VOType extends IBaseVO> implements Serializable, IAssociation
{

	private static final long serialVersionUID = 8530735878505796953L;

	private int firstResult = 0;

	private int maxResults = 0;

	private EntityVO entityVO;

	private LogicalOperatorVO logicalOperator = LogicalOperatorVO.AND;

	public GenericFilterVO()
	{
	}

	public GenericFilterVO(Class<VOType> voClass)
	{
		this(voClass.getName());
	}

	public GenericFilterVO(String voClassName)
	{
		this.entityVO = new EntityVO(voClassName);
	}

	/** {@inheritDoc} */
	@Override
	public AssociationVO addAssociation(IAttributeDescriptor<?> attributeDescriptor)
	{
		return this.entityVO.addAssociation(attributeDescriptor.getAttributeName());
	}

	/** {@inheritDoc} */
	@Override
	public AssociationVO addAssociation(String field)
	{
		return this.entityVO.addAssociation(field);
	}

	/** {@inheritDoc} */
	public GenericFilterVO<VOType> addCriteria(IAttributeDescriptor<?> attributeDescriptor, Object value)
	{
		this.entityVO.addCriteria(attributeDescriptor.getAttributeName(), value);

		return this;
	}

	public void addCriteria(IConditionalExpressionVO conditionalExpressionVO)
	{
		this.entityVO.addCriteria(conditionalExpressionVO);
	}

	public void addCriteria(List<IConditionalExpressionVO> conditionalExpressionVOs)
	{
		this.entityVO.addCriteria(conditionalExpressionVOs);
	}

	public void addCriteria(String field, Object value)
	{
		this.entityVO.addCriteria(field, value);
	}

	public void addCriteria(String field, Object value, RelationalOperator relationalOperator)
	{
		this.entityVO.addCriteria(field, value, relationalOperator);
	}

	public void addOrderBy(String field, ORDER_DIRECTION orderDirection)
	{
		this.entityVO.addOrderBy(field, orderDirection);
	}

	public void clearOrderBy()
	{
		this.entityVO.getOrderBy().clear();

	}

	public EntityVO getEntity()
	{
		return this.entityVO;
	}

	public int getFirstResult()
	{
		return this.firstResult;
	}

	public int getMaxResults()
	{
		return this.maxResults;
	}

	public String getVOClassName()
	{
		return this.entityVO.getVoClassName();
	}

	public void setFirstResult(int firstResult)
	{
		this.firstResult = firstResult;
	}

	public void setMaxResults(int maxResults)
	{
		this.maxResults = maxResults;
	}

	public LogicalOperatorVO getLogicalOperator()
	{
		return this.logicalOperator;
	}

	public void setLogicalOperator(LogicalOperatorVO logicalOperator)
	{
		this.logicalOperator = logicalOperator;
	}

}
