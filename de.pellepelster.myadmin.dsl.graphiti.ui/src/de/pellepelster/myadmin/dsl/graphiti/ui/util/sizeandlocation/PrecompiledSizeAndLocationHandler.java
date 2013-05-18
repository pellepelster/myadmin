package de.pellepelster.myadmin.dsl.graphiti.ui.util.sizeandlocation;

import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;

public class PrecompiledSizeAndLocationHandler extends BaseSizeAndLocationHandler<PrecompiledSizeAndLocationHandler>
{
	public PrecompiledSizeAndLocationHandler()
	{
	}

	public static PrecompiledSizeAndLocationHandler create()
	{
		return new PrecompiledSizeAndLocationHandler();
	}

	@Override
	protected PrecompiledSizeAndLocationHandler getQueryInstance()
	{
		return this;
	}

	public SizeAndLocation computeSizeAndLocation(SizeAndLocation parentSizeAndLocation)
	{
		return super.computeSizeAndLocationInternal(parentSizeAndLocation);
	}

	public PrecompiledSizeAndLocationHandler setSizeAndLocation(ContainerShape containerShape, GraphicsAlgorithm graphicsAlgorithm)
	{
		return setSizeAndLocation(getSizeAndLocation(containerShape.getGraphicsAlgorithm()), graphicsAlgorithm);
	}

	public PrecompiledSizeAndLocationHandler setSizeAndLocation(GraphicsAlgorithm parentGraphicsAlgorithm, GraphicsAlgorithm graphicsAlgorithm)
	{
		return setSizeAndLocation(getSizeAndLocation(parentGraphicsAlgorithm), graphicsAlgorithm);
	}

	public int[] getPoints(GraphicsAlgorithm parentSizeAndLocation)
	{
		return getPoints(getSizeAndLocation(parentSizeAndLocation));
	}

	public PrecompiledSizeAndLocationHandler updatePoints(GraphicsAlgorithm parentGraphicsAlgorithm, Polyline headerLine)
	{
		return updatePoints(getSizeAndLocation(parentGraphicsAlgorithm), headerLine);
	}

}
