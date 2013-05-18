package de.pellepelster.myadmin.dsl.graphiti.ui.entity;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.AddContextQuery;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;

public class EntityAddFeature extends BaseContainerAddFeature<Entity>
{

	public EntityAddFeature(IFeatureProvider fp)
	{
		super(fp, Entity.class);
	}

	@Override
	public boolean canAdd(IAddContext context)
	{
		return AddContextQuery.create(context).newObjectTypeIs(Entity.class).targetContainerTypeIs(Diagram.class).result();
	}

	@Override
	public PictogramElement add(IAddContext context)
	{
		return addInternal(context);
	}

	@Override
	protected String getNameText(Entity businessObject)
	{
		return businessObject.getName();
	}

}