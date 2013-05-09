package de.pellepelster.myadmin.dsl.graphiti.ui.base.attribute;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.Reason;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.BaseUpdateFeature;

public class BaseAttributeUpdateFeature<T extends EObject> extends BaseUpdateFeature
{
	private Class<T> businessObjectClass;

	private EStructuralFeature nameStructuralFeature;

	public BaseAttributeUpdateFeature(IFeatureProvider fp, Class<T> businessObjectClass, EStructuralFeature nameStructuralFeature)
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
	public boolean update(IUpdateContext context)
	{
		return updateText(context, BaseAttributeAddFeature.NAME_TEXT_ID, this.nameStructuralFeature);
	}

	@Override
	public IReason updateNeeded(IUpdateContext context)
	{
		if (checkUpdateNeededText(context, BaseAttributeAddFeature.NAME_TEXT_ID, this.nameStructuralFeature))
		{
			return Reason.createTrueReason("Name is out of date");
		}

		return Reason.createFalseReason();
	}
}