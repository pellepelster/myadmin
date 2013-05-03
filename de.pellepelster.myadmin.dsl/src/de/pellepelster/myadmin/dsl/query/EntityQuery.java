package de.pellepelster.myadmin.dsl.query;

import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;

public class EntityQuery
{
	private Entity entity;

	public EntityQuery(Entity entity)
	{
		this.entity = entity;
	}

	public Entity getEntity()
	{
		return entity;
	}

	public AttributesQuery getAttributes()
	{
		return new AttributesQuery(entity.getAttributes());
	}

}
