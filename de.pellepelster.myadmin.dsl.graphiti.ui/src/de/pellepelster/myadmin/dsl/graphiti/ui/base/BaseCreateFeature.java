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

public abstract class BaseCreateFeature<BO extends EObject> extends AbstractCreateFeature
{
	@Inject
	private ModelDiagramModelService modelService;

	private Class<BO> businessObjectClass;

	public BaseCreateFeature(IFeatureProvider fp, Class<BO> businessObjectClass, String name, String description)
	{
		super(fp, name, description);
		this.modelService = ModelDiagramModelService.getModelService(fp.getDiagramTypeProvider());
		this.businessObjectClass = businessObjectClass;
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

	public abstract BO createAndAddToModelInternal(ICreateContext context, String className);

	@Override
	public Object[] create(ICreateContext context)
	{

		int boElementCount = ModelQuery.createQuery(getModelService().getModel()).getAllByType(this.businessObjectClass).size();

		String newClassName = String.format("%s%s%d", Messages.New, getName(), boElementCount++);

		newClassName = newClassName.replaceAll(" ", "");

		if (newClassName == null || newClassName.trim().length() == 0)
		{
			return EMPTY;
		}

		BO newElement = createAndAddToModelInternal(context, newClassName);

		addGraphicalRepresentation(context, newElement);

		getFeatureProvider().getDirectEditingInfo().setActive(true);

		return new Object[] { newElement };
	}

}
