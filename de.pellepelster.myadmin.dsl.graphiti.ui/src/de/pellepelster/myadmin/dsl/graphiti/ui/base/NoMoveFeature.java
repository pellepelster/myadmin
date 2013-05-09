package de.pellepelster.myadmin.dsl.graphiti.ui.base;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature;

public class NoMoveFeature extends DefaultMoveShapeFeature
{

	public NoMoveFeature(IFeatureProvider fp)
	{
		super(fp);
	}

	@Override
	public boolean canMoveShape(IMoveShapeContext context)
	{
		return false;
	}

}
