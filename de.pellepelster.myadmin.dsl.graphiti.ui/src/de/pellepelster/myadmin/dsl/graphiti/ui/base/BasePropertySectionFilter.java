package de.pellepelster.myadmin.dsl.graphiti.ui.base;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;

public abstract class BasePropertySectionFilter<BO extends EObject> extends AbstractPropertySectionFilter
{

	private final Class<BO> businessObjectClass;

	public BasePropertySectionFilter(Class<BO> businessObjectClass)
	{
		super();
		this.businessObjectClass = businessObjectClass;
	}

	@Override
	protected boolean accept(PictogramElement pe)
	{
		EObject eObject = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);

		if (this.businessObjectClass.isAssignableFrom(eObject.getClass()))
		{
			return true;
		}

		return false;
	}
}