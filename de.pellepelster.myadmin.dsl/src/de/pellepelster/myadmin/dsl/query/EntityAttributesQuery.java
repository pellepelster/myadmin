package de.pellepelster.myadmin.dsl.query;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import com.google.common.collect.Collections2;

import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;
import de.pellepelster.myadmin.dsl.query.predicates.EObjectStructuralFeatureEqualsPredicate;

public class EntityAttributesQuery extends BaseEObjectCollectionQuery<EntityAttribute>
{
	public static EntityAttributesQuery create(Collection<EntityAttribute> entityAttributes)
	{
		return new EntityAttributesQuery(entityAttributes);
	}

	private EntityAttributesQuery(Collection<EntityAttribute> entityAttributes)
	{
		super(entityAttributes);
	}

	public <T extends EObject> TypeQuery<T> getTypes(Class<T> attributeTypeClass)
	{
		return new TypeQuery<T>(transform(MyAdminDslPackage.Literals.ENTITY_ATTRIBUTE__TYPE, attributeTypeClass));
	}

	public EntityAttributeQuery getAttributeByName(String attributeName)
	{
		setList(Collections2
				.filter(getList(), EObjectStructuralFeatureEqualsPredicate.create(MyAdminDslPackage.Literals.ENTITY_ATTRIBUTE__NAME, attributeName)));

		return EntityAttributeQuery.create(getSingleResult());
	}
}
