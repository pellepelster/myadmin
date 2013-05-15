package de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.attribute.BaseAttributeLayoutFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;

public class EntityAttributeLayoutFeature extends BaseAttributeLayoutFeature<EntityAttribute>
{
	public EntityAttributeLayoutFeature(IFeatureProvider fp)
	{
		super(fp, EntityAttribute.class);
	}
}