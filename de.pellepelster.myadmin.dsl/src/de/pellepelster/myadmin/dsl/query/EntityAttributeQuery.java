package de.pellepelster.myadmin.dsl.query;

import de.pellepelster.myadmin.dsl.myAdminDsl.DatatypeType;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityType;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatypeType;
import de.pellepelster.myadmin.dsl.myAdminDsl.Type;

public class EntityAttributeQuery extends BaseEObjectQuery<EntityAttribute>
{
	public static EntityAttributeQuery create(EntityAttribute entityAttribute)
	{
		return new EntityAttributeQuery(entityAttribute);
	}

	private EntityAttributeQuery(EntityAttribute entityAttribute)
	{
		super(entityAttribute);
	}

	public Type getType()
	{
		return (Type) getObject().eGet(MyAdminDslPackage.Literals.ENTITY_ATTRIBUTE__TYPE);
	}

	public Entity getEntity()
	{
		Type type = getType();

		if (type instanceof EntityType)
		{
			EntityType entityType = (EntityType) type;
			return entityType.getType();
		}
		else if (type instanceof ReferenceDatatypeType)
		{
			ReferenceDatatypeType referenceDatatype = (ReferenceDatatypeType) type;
			return referenceDatatype.getType().getEntity();
		}
		else if (type instanceof DatatypeType)
		{
			DatatypeType datatypeType = (DatatypeType) type;

			if (datatypeType.getType() instanceof ReferenceDatatype)
			{
				ReferenceDatatype referenceDatatype = (ReferenceDatatype) datatypeType.getType();
				return referenceDatatype.getEntity();
			}
		}

		return null;
	}

	public boolean isEntity()
	{
		return getEntity() != null;
	}

}
