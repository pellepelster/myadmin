package de.pellepelster.myadmin.dsl.graphiti.ui.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

import de.pellepelster.myadmin.dsl.graphiti.ui.query.ContainerShapeQuery;

public class BaseClassLayoutFeature<T extends EObject> extends AbstractLayoutFeature
{

	private Class<T> businessObjectClass;

	private static final int MIN_HEIGHT = 30;

	private static final int MIN_WIDTH = 50;

	public BaseClassLayoutFeature(IFeatureProvider fp, Class<T> businessObjectClass)
	{
		super(fp);
		this.businessObjectClass = businessObjectClass;
	}

	@Override
	public boolean canLayout(ILayoutContext context)
	{
		PictogramElement pe = context.getPictogramElement();

		if (!(pe instanceof ContainerShape))
		{
			return false;
		}

		EList<EObject> businessObjects = pe.getLink().getBusinessObjects();

		return businessObjects.size() == 1 && this.businessObjectClass.isAssignableFrom(businessObjects.get(0).getClass());
	}

	@Override
	public boolean layout(ILayoutContext context)
	{
		boolean anythingChanged = false;

		ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
		GraphicsAlgorithm containerGa = containerShape.getGraphicsAlgorithm();

		// height
		if (containerGa.getHeight() < MIN_HEIGHT)
		{
			containerGa.setHeight(MIN_HEIGHT);
			anythingChanged = true;
		}

		// width
		if (containerGa.getWidth() < MIN_WIDTH)
		{
			containerGa.setWidth(MIN_WIDTH);
			anythingChanged = true;
		}

		int containerWidth = containerGa.getWidth();

		IGaService gaService = Graphiti.getGaService();

		// scale header line
		Polyline headerLine = ContainerShapeQuery.create(containerShape).getPolylineById(BaseClassAddFeature.HEADER_LINE_ID);

		if (containerWidth != headerLine.getWidth())
		{

			Point secondPoint = headerLine.getPoints().get(1);
			Point newSecondPoint = gaService.createPoint(containerWidth, secondPoint.getY());
			headerLine.getPoints().set(1, newSecondPoint);

			anythingChanged = true;
		}

		for (Shape shape : containerShape.getChildren())
		{
			GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();
			IDimension size = gaService.calculateSize(graphicsAlgorithm);

			if (containerWidth != size.getWidth())
			{
				gaService.setWidth(graphicsAlgorithm, containerWidth);
				anythingChanged = true;
			}
		}

		return anythingChanged;
	}
}