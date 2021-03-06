package de.pellepelster.myadmin.dsl.graphiti.ui.base;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.swt.widgets.Shell;

import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.ModelRoot;
import de.pellepelster.myadmin.dsl.myAdminDsl.SimpleTypes;
import de.pellepelster.myadmin.dsl.query.ModelQuery;

public class TypeSelectionDialog extends BaseModelSelectionDialog
{
	private IFeatureProvider featureProvider;

	public TypeSelectionDialog(Shell shell, IFeatureProvider featureProvider)
	{
		super(shell);
		this.featureProvider = featureProvider;

	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider, ItemsFilter itemsFilter, IProgressMonitor progressMonitor) throws CoreException
	{
		ModelRoot modelRoot = (ModelRoot) this.featureProvider.getBusinessObjectForPictogramElement(this.featureProvider.getDiagramTypeProvider().getDiagram());

		for (SimpleTypes simpleType : SimpleTypes.VALUES)
		{
			contentProvider.add(simpleType, itemsFilter);
		}

		for (EObject eObject : ModelQuery.createQuery(modelRoot).getAllByType(Datatype.class))
		{
			contentProvider.add(eObject, itemsFilter);
		}
	}

}
