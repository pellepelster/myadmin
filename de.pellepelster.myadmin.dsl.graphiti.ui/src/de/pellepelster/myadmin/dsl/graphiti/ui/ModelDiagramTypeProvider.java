package de.pellepelster.myadmin.dsl.graphiti.ui;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.pellepelster.myadmin.dsl.graphiti.ui.util.DiagramUtil;

public class ModelDiagramTypeProvider extends AbstractDiagramTypeProvider implements IDiagramTypeProvider
{

	public static String MODEL_DIAGRAM_TYPE_ID = "de.pellepelster.myadmin.dsl.graphiti.ui.modeldiagramtype";

	public ModelDiagramTypeProvider()
	{
		super();
		setFeatureProvider(new ModelFeatureProvider(this));
	}

	@Override
	public Diagram getDiagram()
	{
		Diagram diagram = super.getDiagram();

		DiagramUtil.updateDiagram(diagram);

		return diagram;
	}

}
