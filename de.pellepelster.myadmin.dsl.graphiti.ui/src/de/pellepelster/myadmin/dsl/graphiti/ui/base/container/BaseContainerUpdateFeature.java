package de.pellepelster.myadmin.dsl.graphiti.ui.base.container;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.services.Graphiti;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.BaseUpdateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute.EntityAttributeAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.ContainerShapeQuery;

public abstract class BaseContainerUpdateFeature<BO extends EObject, ATTRIBUTE extends EObject> extends BaseUpdateFeature
{
	private Class<BO> businessObjectClass;

	private EStructuralFeature nameStructuralFeature;

	public BaseContainerUpdateFeature(IFeatureProvider fp, Class<BO> businessObjectClass, EStructuralFeature nameStructuralFeature)
	{
		super(fp);
		this.businessObjectClass = businessObjectClass;
		this.nameStructuralFeature = nameStructuralFeature;
	}

	@Override
	public boolean canUpdate(IUpdateContext context)
	{
		Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());

		return this.businessObjectClass.isAssignableFrom(bo.getClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReason updateNeeded(IUpdateContext context)
	{
		ContainerShape entityContainerShape = (ContainerShape) context.getPictogramElement();

		// layout entity attributes
		Collection<ContainerShape> attributeContainerShapes = ContainerShapeQuery.create(entityContainerShape).getContainerShapesById(
				EntityAttributeAddFeature.ATTRIBUTE_CONTAINER_ID);

		for (ContainerShape attributeContainerShape : attributeContainerShapes)
		{
			ATTRIBUTE attribute = (ATTRIBUTE) getBusinessObjectForPictogramElement(attributeContainerShape);

			if (!getAttributes(context).contains(attribute))
			{
				return Reason.createTrueReason("Attributes out of date");
			}
		}

		if (checkUpdateNeededText(context, BaseContainerAddFeature.CONTAINER_NAME_TEXT_ID, this.nameStructuralFeature, null))
		{
			return Reason.createTrueReason("Name is out of date");
		}

		return Reason.createFalseReason();
	}

	@Override
	public boolean update(IUpdateContext context)
	{
		return updateAttributes(context) && updateText(context, BaseContainerAddFeature.CONTAINER_NAME_TEXT_ID, this.nameStructuralFeature, null);
	}

	protected abstract List<ATTRIBUTE> getAttributes(IPictogramElementContext context);

	private boolean updateAttributes(IUpdateContext context)
	{
		ContainerShape containerShape = (ContainerShape) context.getPictogramElement();

		Collection<ContainerShape> attributeContainerShapes = ContainerShapeQuery.create(containerShape).getContainerShapesById(
				EntityAttributeAddFeature.ATTRIBUTE_CONTAINER_ID);

		// remove attributes that do not exist in model
		Iterator<ContainerShape> it = attributeContainerShapes.iterator();
		while (it.hasNext())
		{
			ContainerShape attributeContainerShape = it.next();
			@SuppressWarnings("unchecked")
			ATTRIBUTE attribute = (ATTRIBUTE) getBusinessObjectForPictogramElement(attributeContainerShape);

			if (!getAttributes(context).contains(attribute))
			{
				Graphiti.getPeService().deletePictogramElement(attributeContainerShape);
				it.remove();
			}
		}

		return true;
	}
}