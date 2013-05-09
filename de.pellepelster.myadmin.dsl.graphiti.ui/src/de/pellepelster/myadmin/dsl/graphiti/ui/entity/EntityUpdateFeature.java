package de.pellepelster.myadmin.dsl.graphiti.ui.entity;

import java.util.List;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IPictogramElementContext;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerUpdateFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class EntityUpdateFeature extends BaseContainerUpdateFeature<Entity, EntityAttribute>
{

	public EntityUpdateFeature(IFeatureProvider fp)
	{
		super(fp, Entity.class, MyAdminDslPackage.Literals.ENTITY__NAME);
	}

	@Override
	protected List<EntityAttribute> getAttributes(IPictogramElementContext context)
	{
		Entity entity = (Entity) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return entity.getAttributes();
	}

}