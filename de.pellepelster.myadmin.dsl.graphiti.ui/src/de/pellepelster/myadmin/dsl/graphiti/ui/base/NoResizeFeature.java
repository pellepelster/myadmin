package de.pellepelster.myadmin.dsl.graphiti.ui.base;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.impl.DefaultResizeShapeFeature;

public class NoResizeFeature extends DefaultResizeShapeFeature
{

	public NoResizeFeature(IFeatureProvider fp)
	{
		super(fp);
	}

	@Override
	public boolean canResizeShape(IResizeShapeContext context)
	{
		return false;
	}

	@Override
	public boolean canExecute(IContext context)
	{
		return false;
	}

}
