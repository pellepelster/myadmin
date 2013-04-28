package de.pellepelster.myadmin.dsl.graphiti.ui;

import org.apache.log4j.Logger;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;

public class MyAdminModelFeatureProvider extends DefaultFeatureProvider
{
	private static Logger LOG = Logger.getLogger(MyAdminModelFeatureProvider.class);

	public MyAdminModelFeatureProvider(IDiagramTypeProvider dtp)
	{
		super(dtp);
	}

	@Override
	public IAddFeature getAddFeature(IAddContext context)
	{
		LOG.error(String.format("getAddFeature, new object class: '%s'", context.getNewObject().getClass().getName()));

		if (context.getNewObject() instanceof Entity)
		{
			return new AddEntityFeature(this);
		}

		return super.getAddFeature(context);
	}

	@Override
	public ICreateFeature[] getCreateFeatures()
	{
		return new ICreateFeature[] { new TutorialCreateEClassFeature(this) };
	}

	@Override
	public IResizeShapeFeature getResizeShapeFeature(IResizeShapeContext context)
	{
		// TODO Auto-generated method stub
		return super.getResizeShapeFeature(context);
	}

}
