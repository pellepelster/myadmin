package de.pellepelster.myadmin.dsl.graphiti.ui.base;

import java.util.Comparator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

import de.pellepelster.myadmin.dsl.graphiti.ui.Activator;
import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.ModelRoot;
import de.pellepelster.myadmin.dsl.myAdminDsl.SimpleTypes;
import de.pellepelster.myadmin.dsl.query.ModelQuery;
import de.pellepelster.myadmin.dsl.ui.ModelUiUtil;

public class TypeSelectionDialog extends FilteredItemsSelectionDialog
{
	private IFeatureProvider featureProvider;

	private static final String DIALOG_SETTINGS = TypeSelectionDialog.class.getName();

	public TypeSelectionDialog(Shell shell, IFeatureProvider featureProvider)
	{
		super(shell, false);

		setListLabelProvider(ModelUiUtil.getLabelProvider());
		this.featureProvider = featureProvider;
	}

	@Override
	protected Control createExtendedContentArea(Composite parent)
	{
		return null;
	}

	@Override
	protected IDialogSettings getDialogSettings()
	{
		IDialogSettings settings = Activator.getDefault().getDialogSettings().getSection(DIALOG_SETTINGS);

		if (settings == null)
		{
			settings = Activator.getDefault().getDialogSettings().addNewSection(DIALOG_SETTINGS);
		}
		return settings;
	}

	@Override
	protected IStatus validateItem(Object item)
	{
		return Status.OK_STATUS;
	}

	@Override
	protected ItemsFilter createFilter()
	{
		return new ItemsFilter()
		{
			@Override
			public boolean matchItem(final Object item)
			{
				return matches(getElementName(item));
			}

			@Override
			public boolean isConsistentItem(final Object p_item)
			{
				return true;
			}
		};
	}

	@Override
	protected Comparator<Object> getItemsComparator()
	{
		return new Comparator<Object>()
		{
			@Override
			public int compare(Object o1, Object o2)
			{
				if (o1 instanceof SimpleTypes && o2 instanceof EObject)
				{
					return -1;
				}

				if (o1 instanceof EObject && o2 instanceof SimpleTypes)
				{
					return 1;
				}

				return TypeSelectionDialog.this.getElementName(o1).compareTo(TypeSelectionDialog.this.getElementName(o2));
			}
		};
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

	@Override
	public String getElementName(Object item)
	{
		return ModelUiUtil.getLabelProvider().getText(item);
	}
}
