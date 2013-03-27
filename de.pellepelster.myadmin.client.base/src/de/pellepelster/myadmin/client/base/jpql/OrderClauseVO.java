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

/**
 * Represents a sorting direction for a VO field
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class OrderClauseVO implements Serializable
{

	public enum ORDER_DIRECTION
	{
		ASC, DESC
	}

	private static final long serialVersionUID = 3951609189212565899L;

	private String field;
	private ORDER_DIRECTION orderType;

	public OrderClauseVO()
	{
	}

	public OrderClauseVO(String field)
	{
		this(field, ORDER_DIRECTION.ASC);
	}

	public OrderClauseVO(String field, ORDER_DIRECTION orderType)
	{
		this.field = field;
		this.orderType = orderType;
	}

	public String getField()
	{
		return this.field;
	}

	public ORDER_DIRECTION getOrderType()
	{
		return this.orderType;
	}

}
