package de.pellepelster.myadmin.dsl.graphiti.ui.base;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

import de.pellepelster.myadmin.dsl.graphiti.ui.MyAdminGraphitiConstants;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.ContainerShapeQuery;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.SizeAndLocation;

public class BaseAttributeLayoutFeature<T extends EObject> extends AbstractLayoutFeature
{

	private Class<T> attributeClass;

	public BaseAttributeLayoutFeature(IFeatureProvider fp, Class<T> attributeClass)
	{
		super(fp);
		this.attributeClass = attributeClass;
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

		return businessObjects.size() == 1 && this.attributeClass.isAssignableFrom(businessObjects.get(0).getClass());
	}

	@Override
	public boolean layout(ILayoutContext context)
	{
		boolean anythingChanged = false;

		IGaService gaService = Graphiti.getGaService();

		ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
		GraphicsAlgorithm containerGa = containerShape.getGraphicsAlgorithm();

		// height
		if (containerGa.getHeight() < MyAdminGraphitiConstants.CLASS_MIN_HEIGHT)
		{
			containerGa.setHeight(MyAdminGraphitiConstants.CLASS_MIN_HEIGHT);
			anythingChanged = true;
		}

		// width
		if (containerGa.getWidth() < MyAdminGraphitiConstants.CLASS_MIN_WIDTH)
		{
			containerGa.setWidth(MyAdminGraphitiConstants.CLASS_MIN_WIDTH);
			anythingChanged = true;
		}

		int containerWidth = containerGa.getWidth();

		// scale header line
		Polyline headerLine = ContainerShapeQuery.create(containerShape).getPolylineById(BaseClassAddFeature.HEADER_LINE_ID);
		anythingChanged = SizeAndLocation.create(containerGa).setYAndHeight(BaseClassAddFeature.HEADER_LINE_Y).updatePoints(headerLine)
				.hasChanged(anythingChanged);

		RoundedRectangle attributesRoundedRectangle = ContainerShapeQuery.create(containerShape).getGraphicsAlgorithmById(
				BaseClassAddFeature.ATTRIBUTES_SHAPE_ID, RoundedRectangle.class);
		anythingChanged = SizeAndLocation.create(containerGa).setYAndShrinkHeight(BaseClassAddFeature.HEADER_LINE_Y)
				.setLocationAndSize(attributesRoundedRectangle).hasChanged(anythingChanged);

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