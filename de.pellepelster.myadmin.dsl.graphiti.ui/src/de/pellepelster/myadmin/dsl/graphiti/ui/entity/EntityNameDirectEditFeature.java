package de.pellepelster.myadmin.dsl.graphiti.ui.entity;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseDirectEditFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class EntityNameDirectEditFeature extends BaseDirectEditFeature<Entity>
{
	public EntityNameDirectEditFeature(IFeatureProvider fp)
	{
		super(fp, Entity.class, MyAdminDslPackage.Literals.ENTITY__NAME);
	}
}