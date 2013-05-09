package de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute;

import java.util.List;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IPictogramElementContext;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerUpdateFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class EntityAttributeUpdateFeature extends BaseContainerUpdateFeature<Entity, Entity>
{

	public EntityAttributeUpdateFeature(IFeatureProvider fp)
	{
		super(fp, Entity.class, MyAdminDslPackage.Literals.ENTITY__NAME);
	}

	@Override
	protected List<Entity> getAttributes(IPictogramElementContext context)
	{
		return null;
	}

}