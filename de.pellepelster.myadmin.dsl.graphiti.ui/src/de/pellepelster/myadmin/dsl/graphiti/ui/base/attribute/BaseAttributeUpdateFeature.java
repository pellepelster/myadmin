package de.pellepelster.myadmin.dsl.graphiti.ui.base.attribute;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.jface.viewers.ILabelProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;
import de.pellepelster.myadmin.dsl.graphiti.ui.base.BaseUpdateFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;
import de.pellepelster.myadmin.dsl.ui.ModelUiUtil;

public class BaseAttributeUpdateFeature<T extends EObject> extends BaseUpdateFeature
{
	private Class<T> businessObjectClass;

	private EStructuralFeature nameStructuralFeature;

	private ILabelProvider labelProvider = ModelUiUtil.getLabelProvider();

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
		return updateText(context, BaseAttributeAddFeature.ATTRIBUTE_NAME_TEXT_ID, this.nameStructuralFeature, null)
				&& updateText(context, BaseAttributeAddFeature.ATTRIBUTE_TYPE_TEXT_ID, MyAdminDslPackage.Literals.ENTITY_ATTRIBUTE__TYPE, this.labelProvider);
	}

	@Override
	public IReason updateNeeded(IUpdateContext context)
	{
		if (checkUpdateNeededText(context, BaseAttributeAddFeature.ATTRIBUTE_NAME_TEXT_ID, this.nameStructuralFeature, null))
		{
			return Reason.createTrueReason(Messages.NameIsOutOfDate);
		}

		if (checkUpdateNeededText(context, BaseAttributeAddFeature.ATTRIBUTE_TYPE_TEXT_ID, MyAdminDslPackage.Literals.ENTITY_ATTRIBUTE__TYPE,
				this.labelProvider))
		{
			return Reason.createTrueReason(Messages.TypeIsOutOfDate);
		}

		return Reason.createFalseReason();
	}
}