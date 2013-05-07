package de.pellepelster.myadmin.dsl.graphiti.ui.base;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.pellepelster.myadmin.dsl.graphiti.ui.query.ContainerShapeQuery;

public abstract class BaseUpdateFeature extends AbstractUpdateFeature
{

	public BaseUpdateFeature(IFeatureProvider fp)
	{
		super(fp);
	}

	protected ContainerShape getContainerShape(IUpdateContext context)
	{
		PictogramElement pictogramElement = context.getPictogramElement();

		if (pictogramElement instanceof ContainerShape)
		{
			return (ContainerShape) pictogramElement;
		}
		else
		{
			throw new RuntimeException(String.format("ContainerShape expected but got '%s'", pictogramElement.getClass().getName()));
		}
	}

	protected Text getText(IUpdateContext context, String textElementId)
	{
		ContainerShape containerShape = getContainerShape(context);
		return ContainerShapeQuery.create(containerShape).getTextById(textElementId);
	}

	protected EObject getEBusinessObject(IUpdateContext context)
	{
		Object businessObject = getBusinessObjectForPictogramElement(context.getPictogramElement());

		if (businessObject instanceof EObject)
		{
			return (EObject) businessObject;
		}

		return null;
	}

	protected Object getStructuralFeatureFromBusinessObject(IUpdateContext context, EStructuralFeature eStructuralFeature)
	{
		EObject eObject = getEBusinessObject(context);
		return eObject.eGet(eStructuralFeature);
	}

	protected void setStructuralFeatureToBusinessObject(IUpdateContext context, EStructuralFeature eStructuralFeature, Object newValue)
	{
		EObject eObject = getEBusinessObject(context);
		eObject.eSet(eStructuralFeature, newValue);
	}

	public boolean checkUpdateNeededText(IUpdateContext context, String textElementId, EStructuralFeature eStructuralFeature)
	{
		String textValue = getText(context, textElementId).getValue();
		Object eObjectValue = getStructuralFeatureFromBusinessObject(context, eStructuralFeature);

		return ((textValue == null && textValue != null) || (textValue != null && !textValue.equals(eObjectValue)));
	}

	public boolean updateText(IUpdateContext context, String textElementId, EStructuralFeature eStructuralFeature)
	{
		Object eObjectValue = getStructuralFeatureFromBusinessObject(context, eStructuralFeature);
		Text text = getText(context, textElementId);

		if (text != null)
		{
			text.setValue(String.valueOf(eObjectValue));
			return true;
		}
		else
		{
			return false;
		}
	}
}
