package de.pellepelster.myadmin.dsl.graphiti.ui.base.attribute;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.impl.DefaultRemoveFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public abstract class BaseAttributeRemoveFeature<T extends EObject> extends DefaultRemoveFeature
{

	public BaseAttributeRemoveFeature(IFeatureProvider fp)
	{
		super(fp);
	}

	@Override
	public void remove(IRemoveContext context)
	{
		ContainerShape entityContainerShape = null;
		PictogramElement pe = context.getPictogramElement();

		if (pe instanceof ContainerShape)
		{
			entityContainerShape = ((ContainerShape) pe).getContainer();
		}

		super.remove(context);

		layoutPictogramElement(entityContainerShape);
	}
}
