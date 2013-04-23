package de.pellepelster.myadmin.dsl.graphiti.ui;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

public class MyAdminModelFeatureProvider extends DefaultFeatureProvider
{

	public MyAdminModelFeatureProvider(IDiagramTypeProvider dtp)
	{
		super(dtp);

		// ResourceSet rs = new ResourceSetImpl();
		// Resource r = rs.getResource(uriOfYourTextualFile, true);
		// List<EObject> contentOfYourFile = r.getContents();
	}

	@Override
	public IAddFeature getAddFeature(IAddContext context)
	{
		if (context.getNewObject() instanceof EClass)
		{
			return new TutorialAddEClassFeature(this);
		}

		return super.getAddFeature(context);
	}

	@Override
	public ICreateFeature[] getCreateFeatures()
	{
		return new ICreateFeature[] { new TutorialCreateEClassFeature(this) };
	}

}
