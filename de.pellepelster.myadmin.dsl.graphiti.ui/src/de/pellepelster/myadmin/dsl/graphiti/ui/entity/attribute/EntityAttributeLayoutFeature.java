package de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerLayoutFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;

public class EntityAttributeLayoutFeature extends BaseContainerLayoutFeature<Entity>
{
	public EntityAttributeLayoutFeature(IFeatureProvider fp)
	{
		super(fp, Entity.class);
	}
}