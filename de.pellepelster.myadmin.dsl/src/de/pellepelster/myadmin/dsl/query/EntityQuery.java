package de.pellepelster.myadmin.dsl.query;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.dsl.myAdminDsl.DatatypeType;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityType;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatypeType;

public class EntityQuery
{
	private Entity entity;

	public EntityQuery(Entity entity)
	{
		this.entity = entity;
	}

	public Entity getEntity()
	{
		return this.entity;
	}

	public List<Entity> getReferencedEntities()
	{
		List<Entity> entities = new ArrayList<Entity>();

		entities.addAll(AttributesQuery.create(this.entity.getAttributes()).getTypes(EntityType.class)
				.getFeatures(MyAdminDslPackage.Literals.ENTITY_TYPE__TYPE, Entity.class));

		entities.addAll(AttributesQuery.create(this.entity.getAttributes()).getTypes(ReferenceDatatypeType.class)
				.getFeaturesQuery(MyAdminDslPackage.Literals.REFERENCE_DATATYPE_TYPE__TYPE, ReferenceDatatype.class)
				.getFeatures(MyAdminDslPackage.Literals.REFERENCE_DATATYPE__ENTITY, Entity.class));

		entities.addAll(AttributesQuery.create(this.entity.getAttributes()).getTypes(DatatypeType.class)
				.getFeaturesQuery(MyAdminDslPackage.Literals.DATATYPE_TYPE__TYPE, ReferenceDatatype.class)
				.getFeatures(MyAdminDslPackage.Literals.REFERENCE_DATATYPE__ENTITY, Entity.class));

		return entities;
	}

	public AttributesQuery getAttributes()
	{
		return AttributesQuery.create(this.entity.getAttributes());
	}

}
