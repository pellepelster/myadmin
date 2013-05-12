package de.pellepelster.myadmin.dsl.graphiti.ui.base.attribute;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

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
		boolean hasChanged = false;

		IGaService gaService = Graphiti.getGaService();

		ContainerShape attributeContainerShape = (ContainerShape) context.getPictogramElement();
		GraphicsAlgorithm attributeConatinerGa = attributeContainerShape.getGraphicsAlgorithm();

		// attribute name
		Text nameText = ContainerShapeQuery.create(attributeContainerShape).getTextById(BaseAttributeAddFeature.ATTRIBUTE_NAME_TEXT_ID);
		hasChanged = SizeAndLocation.create(attributeContainerShape).setWidthFactor(0.5).setLocationAndSize(nameText).hasChanged(hasChanged);

		Text typeText = ContainerShapeQuery.create(attributeContainerShape).getTextById(BaseAttributeAddFeature.ATTRIBUTE_TYPE_TEXT_ID);
		hasChanged = SizeAndLocation.create(attributeContainerShape).setWidthFactor(0.5).setLocationAndSize(typeText).hasChanged(hasChanged);

		return hasChanged;
	}
}