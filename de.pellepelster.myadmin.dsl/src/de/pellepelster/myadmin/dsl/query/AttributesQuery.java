package de.pellepelster.myadmin.dsl.query;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class AttributesQuery extends BaseEObjectQuery<EntityAttribute>
{

	public AttributesQuery(Collection<EntityAttribute> entityAttributes)
	{
		super(entityAttributes);
	}

	public <T extends EObject> TypeQuery<T> getTypes(Class<T> attributeTypeClass)
	{
		return new TypeQuery<T>(transform(MyAdminDslPackage.Literals.ENTITY_ATTRIBUTE__TYPE, attributeTypeClass));
	}
}
