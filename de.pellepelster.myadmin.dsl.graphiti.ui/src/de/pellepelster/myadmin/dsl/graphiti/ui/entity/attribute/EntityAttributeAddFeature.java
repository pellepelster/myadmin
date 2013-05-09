package de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.attribute.BaseAttributeAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.AddContextQuery;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;

public class EntityAttributeAddFeature extends BaseAttributeAddFeature<EntityAttribute>
{

	public EntityAttributeAddFeature(IFeatureProvider fp)
	{
		super(fp, EntityAttribute.class);
	}

	@Override
	public boolean canAdd(IAddContext context)
	{
		return AddContextQuery.create(context).newObjectTypeIs(EntityAttribute.class).targetContainerElementIdIs(BaseContainerAddFeature.CONTAINER_ID).result();
	}

	@Override
	public PictogramElement add(IAddContext context)
	{
		return addInternal(context);
	}

	@Override
	protected String getNameText(EntityAttribute businessObject)
	{
		return businessObject.getName();
	}

}