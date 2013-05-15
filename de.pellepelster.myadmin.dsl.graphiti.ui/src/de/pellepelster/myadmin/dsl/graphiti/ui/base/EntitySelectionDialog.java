package de.pellepelster.myadmin.dsl.graphiti.ui.base;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.swt.widgets.Shell;

import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.ModelRoot;
import de.pellepelster.myadmin.dsl.query.ModelQuery;

public class EntitySelectionDialog extends BaseModelSelectionDialog
{
	private IFeatureProvider featureProvider;

	public EntitySelectionDialog(Shell shell, IFeatureProvider featureProvider)
	{
		super(shell);
		this.featureProvider = featureProvider;

	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider, ItemsFilter itemsFilter, IProgressMonitor progressMonitor) throws CoreException
	{
		ModelRoot modelRoot = (ModelRoot) this.featureProvider.getBusinessObjectForPictogramElement(this.featureProvider.getDiagramTypeProvider().getDiagram());

		for (EObject eObject : ModelQuery.createQuery(modelRoot).getAllByType(Entity.class))
		{
			contentProvider.add(eObject, itemsFilter);
		}
	}

}
