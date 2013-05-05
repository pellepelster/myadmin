package de.pellepelster.myadmin.dsl.graphiti.ui.util;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.ITargetContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import com.google.inject.Inject;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;
import de.pellepelster.myadmin.dsl.graphiti.ui.ModelDiagramModelService;
import de.pellepelster.myadmin.dsl.myAdminDsl.AbstractElement;
import de.pellepelster.myadmin.dsl.myAdminDsl.PackageDeclaration;
import de.pellepelster.myadmin.dsl.query.ModelQuery;
import de.pellepelster.myadmin.ui.util.MyAdminProjectUtil;

public abstract class BaseClassCreateFeature extends AbstractCreateFeature
{
	@Inject
	private ModelDiagramModelService modelService;

	public BaseClassCreateFeature(IFeatureProvider fp, String name, String description)
	{
		super(fp, name, description);
		this.modelService = ModelDiagramModelService.getModelService(fp.getDiagramTypeProvider());
	}

	protected ModelDiagramModelService getModelService()
	{
		return this.modelService;
	}

	protected PackageDeclaration getPackage(ITargetContext context)
	{
		Diagram diagram = (Diagram) context.getTargetContainer();

		String[] projectNameSegments = MyAdminProjectUtil.splitFQDNProjectName(diagram.getName());

		return ModelQuery.createQuery(getModelService().getModel()).getAndCreatePackageByName(projectNameSegments[0]);
	}

	@Override
	public Object[] create(ICreateContext context)
	{
		String newClassName = String.format("%s%s", Messages.New, getName());

		if (newClassName == null || newClassName.trim().length() == 0)
		{
			return EMPTY;
		}

		AbstractElement abstractElement = createInternal(context, newClassName);

		getPackage(context).getElements().add(abstractElement);
		getFeatureProvider().getDirectEditingInfo().setActive(true);

		addGraphicalRepresentation(context, abstractElement);

		getFeatureProvider().getDirectEditingInfo().setActive(true);

		return new Object[] { abstractElement };
	}

	public abstract AbstractElement createInternal(ICreateContext context, String className);

}
