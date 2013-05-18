package de.pellepelster.myadmin.dsl.query;

import java.util.Collection;
import java.util.List;

import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class EntitiesQuery extends BaseEObjectCollectionQuery<Entity>
{
	public EntitiesQuery(List<Entity> entities)
	{
		super(entities);
	}

	public Collection<Entity> getEntities()
	{
		return getList();
	}

	public EntityQuery getByName(String entityName)
	{
		return new EntityQuery(getSingleByEStructuralFeature(MyAdminDslPackage.Literals.ENTITY__NAME, entityName));
	}
}
