package de.pellepelster.myadmin.dsl.graphiti.ui.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.AbstractDirectEditingFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

public class BaseNameDirectEditFeature<T extends EObject> extends AbstractDirectEditingFeature
{
	private Class<T> businessObjectClass;
	private EStructuralFeature eStructuralFeature;

	public BaseNameDirectEditFeature(IFeatureProvider fp, Class<T> businessObjectClass, EStructuralFeature eStructuralFeature)
	{
		super(fp);
		this.businessObjectClass = businessObjectClass;
		this.eStructuralFeature = eStructuralFeature;
	}

	@Override
	public int getEditingType()
	{
		return TYPE_TEXT;
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context)
	{
		PictogramElement pictogramElement = context.getPictogramElement();
		Object businessObject = getBusinessObjectForPictogramElement(pictogramElement);
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();

		if (this.businessObjectClass.isAssignableFrom(businessObject.getClass()) && ga instanceof Text)
		{
			return true;
		}

		return false;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context)
	{
		PictogramElement pictogrammElement = context.getPictogramElement();

		T businessObject = (T) getBusinessObjectForPictogramElement(pictogrammElement);

		Object boValue = businessObject.eGet(this.eStructuralFeature);

		if (boValue == null)
		{
			return "";
		}
		else
		{
			return boValue.toString();
		}
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context)
	{
		if (value.length() < 1)
			return "Please enter any text as class name.";
		if (value.contains(" "))
			return "Spaces are not allowed in class names.";
		if (value.contains("\n"))
			return "Line breakes are not allowed in class names.";

		// null means, that the value is valid
		return null;
	}

	@Override
	public void setValue(String value, IDirectEditingContext context)
	{
		PictogramElement pe = context.getPictogramElement();
		T businessObject = (T) getBusinessObjectForPictogramElement(pe);

		businessObject.eSet(this.eStructuralFeature, value);
		updatePictogramElement(((Shape) pe).getContainer());
	}
}