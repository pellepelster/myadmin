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
package de.pellepelster.myadmin.client.base.db.vos;

import java.util.HashMap;

public abstract class BaseVO implements IBaseVO
{

	private static final long serialVersionUID = -3339163131084690483L;

	private long oid;

	public BaseVO()
	{
		super();
		this.oid = UUID.uuid().hashCode();
	}

	private HashMap<String, Object> data = new HashMap<String, Object>();

	@Override
	public HashMap<String, Object> getData()
	{
		return this.data;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object object)
	{

		if (this == object)
		{
			return true;
		}
		if (object == null || object.getClass() != this.getClass())
		{
			return false;
		}

		BaseVO baseVO = (BaseVO) object;
		return getOid() == baseVO.getOid();
	}

	@Override
	public long getOid()
	{
		if (isNew())
		{
			return this.oid;
		}
		else
		{
			return getId();
		}
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 37 * hash + (int) (getOid() ^ getOid() >>> 32);
		return hash;
	}

	@Override
	public boolean isNew()
	{
		return getId() == NEW_VO_ID;
	}

	@Override
	public void setOid(long oid)
	{
		this.oid = oid;
	}

	@Override
	public String toString()
	{
		return getClass().getName() + "(id: " + getOid() + ")";
	}
}
