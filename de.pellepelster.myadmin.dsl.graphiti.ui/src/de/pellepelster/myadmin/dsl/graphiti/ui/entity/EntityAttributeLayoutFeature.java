package de.pellepelster.myadmin.dsl.graphiti.ui.entity;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.BaseClassLayoutFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;

public class EntityAttributeLayoutFeature extends BaseClassLayoutFeature<Entity>
{
	public EntityAttributeLayoutFeature(IFeatureProvider fp)
	{
		super(fp, Entity.class);
	}
}