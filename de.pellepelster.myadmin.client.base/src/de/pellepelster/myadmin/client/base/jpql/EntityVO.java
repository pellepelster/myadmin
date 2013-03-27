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

/**
 * Represents a VO with its criteria and associations to load
 * 
 * @author pelle
 * @version $Rev: 662 $, $Date: 2010-08-30 20:58:04 +0200 (Mon, 30 Aug 2010) $
 * 
 */
public class EntityVO extends BaseEntityAssociationVO implements Serializable
{

	private static final long serialVersionUID = -6182336683431474116L;
	private List<OrderClauseVO> orderClauses = new ArrayList<OrderClauseVO>();
	private String voClassName;

	public EntityVO()
	{
	}

	public EntityVO(String voClassName)
	{
		this.voClassName = voClassName;
	}

	public void addOrderBy(String field, OrderClauseVO.ORDER_DIRECTION orderDirection)
	{
		orderClauses.add(new OrderClauseVO(field, orderDirection));
	}

	public List<OrderClauseVO> getOrderBy()
	{
		return orderClauses;
	}

	public String getVoClassName()
	{
		return voClassName;
	}

}