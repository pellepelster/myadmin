package de.pellepelster.myadmin.db;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public abstract class BaseEntity implements IBaseEntity
{
	private long oid;

	@Override
	public boolean isNew()
	{
		return getId() == IBaseVO.NEW_VO_ID;
	}

	@Override
	public void setOid(long oid)
	{
		this.oid = oid;
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
}
