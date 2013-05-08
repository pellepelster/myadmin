package de.pellepelster.myadmin.dsl.graphiti.ui.base;

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
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityAttributeAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.ContainerShapeQuery;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.SizeAndLocation;

public class BaseClassLayoutFeature<T extends EObject> extends AbstractLayoutFeature
{

	private Class<T> businessObjectClass;

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
		boolean hasChanged = false;

		ContainerShape entityContainerShape = (ContainerShape) context.getPictogramElement();
		GraphicsAlgorithm entityContainerGa = entityContainerShape.getGraphicsAlgorithm();

		// scale header line
		Polyline headerLine = ContainerShapeQuery.create(entityContainerShape).getPolylineById(BaseClassAddFeature.HEADER_LINE_ID);
		hasChanged = SizeAndLocation.create(entityContainerGa).setYAndHeight(BaseClassAddFeature.NAME_TEXT_HEIGHT).updatePoints(headerLine)
				.hasChanged(hasChanged);

		// scale text
		Text text = ContainerShapeQuery.create(entityContainerShape).getTextById(BaseClassAddFeature.NAME_TEXT_ID);
		hasChanged = SizeAndLocation.create(entityContainerGa).setHeight(BaseClassAddFeature.NAME_TEXT_HEIGHT).setLocationAndSize(text).hasChanged(hasChanged);

		// layout entity attributes
		Collection<ContainerShape> attributeContainerShapes = ContainerShapeQuery.create(entityContainerShape).getContainerShapesById(
				EntityAttributeAddFeature.ATTRIBUTE_CONTAINER_ID);

		int minHeight = MyAdminGraphitiConstants.CLASS_MIN_HEIGHT;
		if (!attributeContainerShapes.isEmpty())
		{

			int i = 0;
			for (ContainerShape attributeContainerShape : attributeContainerShapes)
			{
				hasChanged = SizeAndLocation
						.create(entityContainerShape)
						.shrinkWidth(MyAdminGraphitiConstants.MARGIN * 2)
						.setHeight(MyAdminGraphitiConstants.ATTRIBUTE_HEIGHT)
						.setY(EntityAddFeature.NAME_TEXT_HEIGHT + MyAdminGraphitiConstants.MARGIN + i
								* (MyAdminGraphitiConstants.ATTRIBUTE_HEIGHT + MyAdminGraphitiConstants.MARGIN)).center()
						.setLocationAndSize(attributeContainerShape.getGraphicsAlgorithm()).hasChanged(hasChanged);
				i++;
			}

			GraphicsAlgorithm lastAttributeContainerShapeGA = Iterators.getLast(attributeContainerShapes.iterator()).getGraphicsAlgorithm();
			minHeight = lastAttributeContainerShapeGA.getY() + lastAttributeContainerShapeGA.getHeight() + MyAdminGraphitiConstants.MARGIN;
		}

		// height
		if (entityContainerGa.getHeight() < minHeight)
		{
			entityContainerGa.setHeight(minHeight);
			hasChanged = true;
		}

		// width
		if (entityContainerGa.getWidth() < MyAdminGraphitiConstants.CLASS_MIN_WIDTH)
		{
			entityContainerGa.setWidth(MyAdminGraphitiConstants.CLASS_MIN_WIDTH);
			hasChanged = true;
		}

		return hasChanged;
	}
}