package de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseDirectEditFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class EntityAttributeNameDirectEditFeature extends BaseDirectEditFeature<EntityAttribute>
{
	public EntityAttributeNameDirectEditFeature(IFeatureProvider fp)
	{
		super(fp, EntityAttribute.class, MyAdminDslPackage.Literals.ENTITY_ATTRIBUTE__NAME);
	}

}