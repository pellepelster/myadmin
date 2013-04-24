package de.pellepelster.myadmin.dsl.ui.labeling;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.google.inject.Inject;

public class MyAdminNavigatorContentLabelProvider extends MyAdminDslLabelProvider
{

	@Inject
	public MyAdminNavigatorContentLabelProvider(AdapterFactoryLabelProvider delegate)
	{
		super(delegate);
	}

	@Override
	public Image getImage(Object element)
	{
		if (element instanceof EObject)
		{
			return super.getImage(element);
		}
		else
		{
			return null;
		}
	}

	@Override
	public String getText(Object element)
	{
		if (element instanceof EObject)
		{
			return super.getText(element);
		}
		else
		{
			return null;
		}
	}

}
