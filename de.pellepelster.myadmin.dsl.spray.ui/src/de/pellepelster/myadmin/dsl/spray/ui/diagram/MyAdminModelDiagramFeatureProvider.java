/*************************************************************************************
 *
 * Generated on Sat Apr 27 22:54:15 CEST 2013 by Spray FeatureProvider.xtend
 * 
 * This file is an extension point: copy to "src" folder to manually add code to this
 * extension point.
 *
 *************************************************************************************/
package de.pellepelster.myadmin.dsl.spray.ui.diagram;

import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.pellepelster.myadmin.dsl.myAdminDsl.Model;

public class MyAdminModelDiagramFeatureProvider extends MyAdminModelDiagramFeatureProviderBase
{

	public MyAdminModelDiagramFeatureProvider(final IDiagramTypeProvider dtp)
	{
		super(dtp);
	}

	@Override
	public void link(PictogramElement pictogramElement, Object businessObject)
	{
		if (businessObject instanceof Model && pictogramElement instanceof Diagram)
		{
			Model model = (Model) businessObject;
			Diagram diagram = (Diagram) pictogramElement;

			if (model.getName() == null || model.getName().isEmpty())
			{
				URI uri = diagram.eResource().getURI();
				uri = uri.trimFragment();
				uri = uri.trimFileExtension();
				String newModelName = uri.lastSegment();
				model.setName(newModelName);
			}

		}

		super.link(pictogramElement, businessObject);
	}

}
