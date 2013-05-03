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

	private static List<Entity> getLinkedEntities(Entity entity, boolean withDuplicates)
	{
		List<Entity> referencedEntities = new ArrayList<Entity>();

		for (EntityAttribute entityAttribute : entity.getAttributes())
		{
			if (entityAttribute.getType() instanceof EntityType)
			{
				EntityType entityReferenceType = (EntityType) entityAttribute.getType();

				if (withDuplicates || !referencedEntities.contains(entityReferenceType.getType()))
				{
					referencedEntities.add(entityReferenceType.getType());
				}
			}
			else if (entityAttribute.getType() instanceof ReferenceDatatypeType)
			{
				ReferenceDatatypeType referenceDatatypeType = (ReferenceDatatypeType) entityAttribute.getType();

				if (withDuplicates || !referencedEntities.contains(referenceDatatypeType.getType().getEntity()))
				{
					referencedEntities.add(referenceDatatypeType.getType().getEntity());
				}

			}
			else if (entityAttribute.getType() instanceof DatatypeType)
			{
				DatatypeType datatypeType = (DatatypeType) entityAttribute.getType();

				if (datatypeType.getType() instanceof ReferenceDatatype)
				{
					ReferenceDatatype referenceDatatype = (ReferenceDatatype) datatypeType.getType();

					if (withDuplicates || !referencedEntities.contains(referenceDatatype.getEntity()))
					{
						referencedEntities.add(referenceDatatype.getEntity());
					}

				}
			}
		}

		return referencedEntities;
	}

}
