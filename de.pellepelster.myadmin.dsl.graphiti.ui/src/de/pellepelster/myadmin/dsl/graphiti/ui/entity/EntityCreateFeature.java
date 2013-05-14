package de.pellepelster.myadmin.dsl.graphiti.ui.entity;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;
import de.pellepelster.myadmin.dsl.graphiti.ui.base.BaseCreateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.CreateContextQuery;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityOptions;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslFactory;

public class EntityCreateFeature extends BaseCreateFeature<Entity>
{
	public EntityCreateFeature(IFeatureProvider fp)
	{
		super(fp, Messages.Entity, Messages.EntityCreate);
	}

	@Override
	public boolean canCreate(ICreateContext context)
	{
		return CreateContextQuery.create(context).targetContainerTypeIs(Diagram.class).result();
	}

	@Override
	public Entity createAndAddToModelInternal(ICreateContext context, String className)
	{
		Entity newEntity = MyAdminDslFactory.eINSTANCE.createEntity();
		newEntity.setName(className);

		EntityOptions entityOptions = MyAdminDslFactory.eINSTANCE.createEntityOptions();
		newEntity.setEntityOptions(entityOptions);

		getPackage(context).getElements().add(newEntity);

		return newEntity;
	}

}