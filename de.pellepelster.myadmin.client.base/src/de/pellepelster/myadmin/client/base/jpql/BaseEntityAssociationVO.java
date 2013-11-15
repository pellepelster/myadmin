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
import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;

/**
 * Represents a VO with its criteria and associations to load
 * 
 * @author pelle
 * @version $Rev: 128 $, $Date: 2009-07-11 15:56:55 +0400 (Sat, 11 Jul 2009) $
 * 
 */
public abstract class BaseEntityAssociationVO implements Serializable, IAssociation
{

	private static final long serialVersionUID = -6182336683431474116L;
	private List<AssociationVO> associations = new ArrayList<AssociationVO>();
	private List<IConditionalExpressionVO> conditionalExpressions = new ArrayList<IConditionalExpressionVO>();

	public BaseEntityAssociationVO()
	{
	}

	/** {@inheritDoc} */
	@Override
	public AssociationVO addAssociation(IAttributeDescriptor<?> attributeDescriptor)
	{
		return addAssociation(attributeDescriptor.getAttributeName());
	}

	/** {@inheritDoc} */
	@Override
	public AssociationVO addAssociation(String field)
	{

		AssociationVO associationVO = new AssociationVO(field);
		this.associations.add(associationVO);

		return associationVO;
	}

	public void addCriteria(IConditionalExpressionVO conditionalExpressionVO)
	{
		this.conditionalExpressions.add(conditionalExpressionVO);
	}

	public void addCriteria(List<IConditionalExpressionVO> conditionalExpressionVOs)
	{
		this.conditionalExpressions.addAll(conditionalExpressionVOs);
	}

	public void addCriteria(String field, Object value)
	{
		this.conditionalExpressions.add(new ConditionalExpressionVO(field, value));
	}

	public void addCriteria(String field, Object value, RelationalOperator relationalOperator)
	{
		this.conditionalExpressions.add(new ConditionalExpressionVO(field, value, relationalOperator));
	}

	public void addCriteria(String voClass, String field, Object value, RelationalOperator relationalOperator)
	{
		this.conditionalExpressions.add(new ConditionalExpressionVO(field, value, relationalOperator));
	}

	public List<AssociationVO> getAssociations()
	{
		return this.associations;
	}

	public List<IConditionalExpressionVO> getCriteria()
	{
		return this.conditionalExpressions;
	}

}