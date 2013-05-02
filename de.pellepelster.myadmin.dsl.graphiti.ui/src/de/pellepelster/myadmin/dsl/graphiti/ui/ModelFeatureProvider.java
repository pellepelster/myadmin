package de.pellepelster.myadmin.dsl.graphiti.ui;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityCreateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityLayoutFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;

public class ModelFeatureProvider extends DefaultFeatureProvider
{
	// private static Logger LOG = Logger.getLogger(ModelFeatureProvider.class);

	public ModelFeatureProvider(IDiagramTypeProvider dtp)
	{
		super(dtp);
	}

	@Override
	public IAddFeature getAddFeature(IAddContext context)
	{
		if (context.getNewObject() instanceof Entity)
		{
			return new EntityAddFeature(this);
		}

		return super.getAddFeature(context);
	}

	@Override
	public ICreateFeature[] getCreateFeatures()
	{
		return new ICreateFeature[] { new EntityCreateFeature(this) };
	}

	@Override
	public ILayoutFeature getLayoutFeature(ILayoutContext context)
	{
		PictogramElement pictogramElement = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pictogramElement);

		if (bo instanceof Entity)
		{
			return new EntityLayoutFeature(this);
		}

		return super.getLayoutFeature(context);
	}

}
