package de.pellepelster.myadmin.dsl.graphiti.ui;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;

public class MyAdminModelDiagramTypeProvider extends AbstractDiagramTypeProvider implements IDiagramTypeProvider
{

	public MyAdminModelDiagramTypeProvider()
	{
		super();
		setFeatureProvider(new MyAdminModelFeatureProvider(this));
	}

}
