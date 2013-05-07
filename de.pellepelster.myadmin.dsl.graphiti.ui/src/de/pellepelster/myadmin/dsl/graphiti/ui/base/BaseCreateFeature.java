package de.pellepelster.myadmin.dsl.graphiti.ui.base;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.ITargetContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import com.google.inject.Inject;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;
import de.pellepelster.myadmin.dsl.graphiti.ui.ModelDiagramModelService;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.DiagramUtil;
import de.pellepelster.myadmin.dsl.myAdminDsl.PackageDeclaration;
import de.pellepelster.myadmin.dsl.query.ModelQuery;

public abstract class BaseCreateFeature<T extends EObject> extends AbstractCreateFeature
{
	@Inject
	private ModelDiagramModelService modelService;

	public BaseCreateFeature(IFeatureProvider fp, String name, String description)
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
		String packageName = DiagramUtil.getPackageName(diagram);

		return ModelQuery.createQuery(getModelService().getModel()).getAndCreatePackageByName(packageName);
	}

	@Override
	public Object[] create(ICreateContext context)
	{
		String newClassName = String.format("%s%s", Messages.New, getName());

		if (newClassName == null || newClassName.trim().length() == 0)
		{
			return EMPTY;
		}

		T newElement = createAndAddToModelInternal(context, newClassName);

		getFeatureProvider().getDirectEditingInfo().setActive(true);
		addGraphicalRepresentation(context, newElement);

		return new Object[] { newElement };
	}

	public abstract T createAndAddToModelInternal(ICreateContext context, String className);

}
