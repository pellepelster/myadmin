package de.pellepelster.myadmin.dsl.graphiti.ui.base.attribute;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.pellepelster.myadmin.dsl.graphiti.ui.query.ContainerShapeQuery;

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
		boolean hasChanged = false;

		ContainerShape attributeContainerShape = (ContainerShape) context.getPictogramElement();

		// attribute name
		Text nameText = ContainerShapeQuery.create(attributeContainerShape).getTextById(BaseAttributeAddFeature.ATTRIBUTE_NAME_TEXT_ID);
		hasChanged = BaseAttributeAddFeature.NAME_TEXT_SIZE_AND_LOCATION_HANDLER.setSizeAndLocation(attributeContainerShape, nameText).hasChanged(hasChanged);

		Text typeText = ContainerShapeQuery.create(attributeContainerShape).getTextById(BaseAttributeAddFeature.ATTRIBUTE_TYPE_TEXT_ID);
		hasChanged = BaseAttributeAddFeature.TYPE_TEXT_SIZE_AND_LOCATION_HANDLER.setSizeAndLocation(attributeContainerShape, typeText).hasChanged(hasChanged);

		return hasChanged;
	}
}