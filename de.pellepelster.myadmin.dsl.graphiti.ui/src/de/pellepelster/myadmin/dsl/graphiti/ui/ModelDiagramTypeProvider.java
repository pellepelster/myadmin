package de.pellepelster.myadmin.dsl.graphiti.ui;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;

public class ModelDiagramTypeProvider extends AbstractDiagramTypeProvider implements IDiagramTypeProvider
{

	public static String MODEL_DIAGRAM_TYPE_ID = "de.pellepelster.myadmin.dsl.graphiti.ui.modeldiagramtype";

	public ModelDiagramTypeProvider()
	{
		super();
		setFeatureProvider(new MyAdminModelFeatureProvider(this));
	}

}
