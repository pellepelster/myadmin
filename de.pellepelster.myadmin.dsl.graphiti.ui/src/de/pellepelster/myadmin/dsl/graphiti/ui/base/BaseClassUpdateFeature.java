package de.pellepelster.myadmin.dsl.graphiti.ui.base;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.services.Graphiti;

import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityAttributeAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.ContainerShapeQuery;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;

public class BaseClassUpdateFeature<T extends EObject> extends BaseUpdateFeature
{
	private Class<T> businessObjectClass;

	private EStructuralFeature nameStructuralFeature;

	public BaseClassUpdateFeature(IFeatureProvider fp, Class<T> businessObjectClass, EStructuralFeature nameStructuralFeature)
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

	@Override
	public IReason updateNeeded(IUpdateContext context)
	{
		ContainerShape entityContainerShape = (ContainerShape) context.getPictogramElement();
		Entity entity = (Entity) getBusinessObjectForPictogramElement(context.getPictogramElement());

		// layout entity attributes
		Collection<ContainerShape> attributeContainerShapes = ContainerShapeQuery.create(entityContainerShape).getContainerShapesById(
				EntityAttributeAddFeature.ATTRIBUTE_CONTAINER_ID);

		for (ContainerShape attributeContainerShape : attributeContainerShapes)
		{
			EntityAttribute entityAttribute = (EntityAttribute) getBusinessObjectForPictogramElement(attributeContainerShape);

			if (!entity.getAttributes().contains(entityAttribute))
			{
				return Reason.createTrueReason("Attributes out of date");
			}
		}

		if (checkUpdateNeededText(context, BaseClassAddFeature.NAME_TEXT_ID, this.nameStructuralFeature))
		{
			return Reason.createTrueReason("Name is out of date");
		}

		return Reason.createFalseReason();
	}

	@Override
	public boolean update(IUpdateContext context)
	{
		boolean succes = true;

		ContainerShape entityContainerShape = (ContainerShape) context.getPictogramElement();
		Entity entity = (Entity) getBusinessObjectForPictogramElement(context.getPictogramElement());

		Collection<ContainerShape> attributeContainerShapes = ContainerShapeQuery.create(entityContainerShape).getContainerShapesById(
				EntityAttributeAddFeature.ATTRIBUTE_CONTAINER_ID);

		// remove entity attributes that do not exist in model
		Iterator<ContainerShape> it = attributeContainerShapes.iterator();
		while (it.hasNext())
		{
			ContainerShape attributeContainerShape = it.next();
			EntityAttribute entityAttribute = (EntityAttribute) getBusinessObjectForPictogramElement(attributeContainerShape);

			if (!entity.getAttributes().contains(entityAttribute))
			{
				Graphiti.getPeService().deletePictogramElement(attributeContainerShape);
				it.remove();
			}
		}

		return succes && updateText(context, BaseClassAddFeature.NAME_TEXT_ID, this.nameStructuralFeature);
	}
}