package de.pellepelster.myadmin.dsl.graphiti.ui.entity;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.util.BaseClassLayoutFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;

public class EntityLayoutFeature extends BaseClassLayoutFeature<Entity>
{
	public EntityLayoutFeature(IFeatureProvider fp)
	{
		super(fp, Entity.class);
	}
}