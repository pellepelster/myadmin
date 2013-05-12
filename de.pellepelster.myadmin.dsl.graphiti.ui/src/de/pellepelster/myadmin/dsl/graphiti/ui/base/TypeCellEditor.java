package de.pellepelster.myadmin.dsl.graphiti.ui.base;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;

public class TypeCellEditor extends DialogCellEditor
{
	private IFeatureProvider featureProvider;

	public static Object CURRENT_VALUE;

	public TypeCellEditor(Composite parent, IFeatureProvider featureProvider)
	{
		super(parent);
		this.featureProvider = featureProvider;
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow)
	{
		TypeSelectionDialog dialog = new TypeSelectionDialog(PlatformUI.createDisplay().getActiveShell(), this.featureProvider);

		dialog.setTitle(Messages.DatatypeSelection);
		dialog.setMessage(Messages.DatatypeSelectFromList);
		dialog.setInitialPattern("?");
		dialog.setBlockOnOpen(true);
		dialog.open();

		return dialog.getFirstResult();
	}

	@Override
	protected void doSetValue(Object value)
	{
		CURRENT_VALUE = value;
		super.doSetValue(value);
	}

}