package de.pellepelster.myadmin.dsl.graphiti.ui.base.container;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.impl.AbstractDirectEditingFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.pellepelster.myadmin.dsl.validation.MyAdminDslJavaValidator;
import de.pellepelster.myadmin.dsl.validation.MyAdminDslJavaValidator.ModelValidationMessage;

public class BaseDirectEditFeature<BO extends EObject> extends AbstractDirectEditingFeature
{
	private Class<BO> businessObjectClass;
	private EStructuralFeature eStructuralFeature;

	public BaseDirectEditFeature(IFeatureProvider fp, Class<BO> businessObjectClass, EStructuralFeature eStructuralFeature)
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

	@SuppressWarnings("unchecked")
	protected BO getBusinessObject(IPictogramElementContext context)
	{
		PictogramElement pictogramElement = context.getPictogramElement();
		Object businessObject = getBusinessObjectForPictogramElement(pictogramElement);

		return (BO) businessObject;
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

	@SuppressWarnings("unchecked")
	@Override
	public String getInitialValue(IDirectEditingContext context)
	{
		PictogramElement pictogrammElement = context.getPictogramElement();
		BO businessObject = (BO) getBusinessObjectForPictogramElement(pictogrammElement);

		Object boValue = businessObject.eGet(this.eStructuralFeature);

		if (boValue == null)
		{
			return "";
		}
		else
		{
			return inititalValueToString(boValue);
		}
	}

	protected String inititalValueToString(Object Value)
	{
		return (Value == null) ? "" : Value.toString();
	}

	private List<ModelValidationMessage> modelValidationMessages = new ArrayList<ModelValidationMessage>();

	private void addValidationMessages(ModelValidationMessage modelValidationMessage)
	{
		this.modelValidationMessages.add(modelValidationMessage);
	}

	private String getValidationResult()
	{
		return (this.modelValidationMessages.isEmpty()) ? null : this.modelValidationMessages.get(0).getMessage();
	}

	private void clearValidationMessages()
	{
		this.modelValidationMessages.clear();
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context)
	{
		clearValidationMessages();

		addValidationMessages(MyAdminDslJavaValidator.validateIdentifier(value));

		return getValidationResult();
	}

	@Override
	public void setValue(String value, IDirectEditingContext context)
	{
		PictogramElement pe = context.getPictogramElement();

		@SuppressWarnings("unchecked")
		BO businessObject = (BO) getBusinessObjectForPictogramElement(pe);
		businessObject.eSet(this.eStructuralFeature, value);

		updatePictogramElement(((Shape) pe).getContainer());
	}
}