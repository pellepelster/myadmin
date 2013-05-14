package de.pellepelster.myadmin.dsl.graphiti.ui.base;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;

public class TypeCellEditor extends DialogCellEditor
{
	private IFeatureProvider featureProvider;

	public static Map<String, Object> CURRENT_VALUE = new HashMap<String, Object>();

	public TypeCellEditor(Composite parent, IFeatureProvider featureProvider)
	{
		super(parent);
		this.featureProvider = featureProvider;
	}

	@Override
	protected Object doGetValue()
	{
		String uuid = UUID.randomUUID().toString();

		Object value = super.doGetValue();

		if (!(value instanceof String))
		{
			CURRENT_VALUE.put(uuid, super.doGetValue());

			return uuid;
		}
		else
		{
			return super.doGetValue();
		}
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

}