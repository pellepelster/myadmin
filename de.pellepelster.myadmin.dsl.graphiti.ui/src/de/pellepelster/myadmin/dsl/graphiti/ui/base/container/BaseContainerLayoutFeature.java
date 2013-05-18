package de.pellepelster.myadmin.dsl.graphiti.ui.base.container;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import com.google.common.collect.Iterators;

import de.pellepelster.myadmin.dsl.graphiti.ui.MyAdminGraphitiConstants;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute.EntityAttributeAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.ContainerShapeQuery;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.sizeandlocation.SizeAndLocationHandler;

public class BaseContainerLayoutFeature<T extends EObject> extends AbstractLayoutFeature
{

	private Class<T> businessObjectClass;

	public BaseContainerLayoutFeature(IFeatureProvider fp, Class<T> businessObjectClass)
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
		boolean hasChanged = false;

		ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
		GraphicsAlgorithm containerGa = containerShape.getGraphicsAlgorithm();

		// scale header line
		Polyline headerLine = ContainerShapeQuery.create(containerShape).getPolylineById(BaseContainerAddFeature.CONTAINER_HEADER_LINE_ID);
		hasChanged = BaseContainerAddFeature.ENTITY_NAME_LINE_SIZE_AND_LOCATION_HANDLER.updatePoints(containerGa, headerLine).hasChanged(hasChanged);

		// scale text
		Text text = ContainerShapeQuery.create(containerShape).getTextById(BaseContainerAddFeature.CONTAINER_NAME_TEXT_ID);
		hasChanged = BaseContainerAddFeature.ENTITY_NAME_TEXT_SIZE_AND_LOCATION_HANDLER.setSizeAndLocation(containerGa, text).hasChanged(hasChanged);

		// layout attributes
		Collection<ContainerShape> attributeContainerShapes = getAttributeContainerShapes(containerShape);

		int minHeight = MyAdminGraphitiConstants.CLASS_MIN_HEIGHT;

		if (!attributeContainerShapes.isEmpty())
		{

			int i = 0;
			for (ContainerShape attributeContainerShape : attributeContainerShapes)
			{
				hasChanged = SizeAndLocationHandler
						.create(containerShape)
						.addWidthOffset(MyAdminGraphitiConstants.MARGIN * -2)
						.setHeight(MyAdminGraphitiConstants.ATTRIBUTE_HEIGHT)
						.setY(BaseContainerAddFeature.CONTAINER_NAME_TEXT_HEIGHT + MyAdminGraphitiConstants.MARGIN + i
								* (MyAdminGraphitiConstants.ATTRIBUTE_HEIGHT + MyAdminGraphitiConstants.MARGIN)).centerHorizontal()
						.setSizeAndLocation(attributeContainerShape.getGraphicsAlgorithm()).hasChanged(hasChanged);
				i++;
			}

			GraphicsAlgorithm lastAttributeContainerShapeGA = Iterators.getLast(attributeContainerShapes.iterator()).getGraphicsAlgorithm();
			minHeight = lastAttributeContainerShapeGA.getY() + lastAttributeContainerShapeGA.getHeight() + MyAdminGraphitiConstants.MARGIN;
		}

		// height
		if (containerGa.getHeight() < minHeight)
		{
			containerGa.setHeight(minHeight);
			hasChanged = true;
		}

		// width
		if (containerGa.getWidth() < MyAdminGraphitiConstants.CLASS_MIN_WIDTH)
		{
			containerGa.setWidth(MyAdminGraphitiConstants.CLASS_MIN_WIDTH);
			hasChanged = true;
		}

		for (ContainerShape attributeContainerShape : attributeContainerShapes)
		{
			layoutPictogramElement(attributeContainerShape);
		}

		return hasChanged;
	}

	protected Collection<ContainerShape> getAttributeContainerShapes(ContainerShape containerShape)
	{
		Collection<ContainerShape> attributeContainerShapes = ContainerShapeQuery.create(containerShape).getContainerShapesById(
				EntityAttributeAddFeature.ATTRIBUTE_CONTAINER_ID);

		return attributeContainerShapes;
	}
}