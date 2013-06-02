package de.pellepelster.myadmin.dsl.util;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.dsl.myAdminDsl.DatatypeType;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityType;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatypeType;

public class EntityModelUtil
{

	public static List<Entity> getLinkedEntitiesWithoutDuplicates(Entity entity)
	{
		return getLinkedEntities(entity, false);
	}

	public static List<Entity> getLinkedEntities(Entity entity)
	{
		return getLinkedEntities(entity, true);
	}

	public static Entity getEntity(EntityAttribute entityAttribute)
	{
		if (entityAttribute.getType() instanceof EntityType)
		{
			EntityType entityReferenceType = (EntityType) entityAttribute.getType();

			return entityReferenceType.getType();
		}
		else if (entityAttribute.getType() instanceof ReferenceDatatypeType)
		{
			ReferenceDatatypeType referenceDatatypeType = (ReferenceDatatypeType) entityAttribute.getType();

			return referenceDatatypeType.getType().getEntity();
		}
		else if (entityAttribute.getType() instanceof DatatypeType)
		{
			DatatypeType datatypeType = (DatatypeType) entityAttribute.getType();

			if (datatypeType.getType() instanceof ReferenceDatatype)
			{
				ReferenceDatatype referenceDatatype = (ReferenceDatatype) datatypeType.getType();

				return referenceDatatype.getEntity();

			}
		}

		return null;
	}

	private static List<Entity> getLinkedEntities(Entity entity, boolean withDuplicates)
	{
		List<Entity> referencedEntities = new ArrayList<Entity>();

		for (EntityAttribute entityAttribute : entity.getAttributes())
		{
			Entity entityAttributeEntity = getEntity(entityAttribute);

			if (entityAttributeEntity != null && (withDuplicates || !referencedEntities.contains(entityAttributeEntity)))
			{
				referencedEntities.add(entityAttributeEntity);
			}
		}

		return referencedEntities;
	}

}
