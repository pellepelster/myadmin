package de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerNameDirectEditFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class EntityAttributeTypeDirectEditFeature extends BaseContainerNameDirectEditFeature<EntityAttribute> // implements
// ICellEditorProvider
{
	public EntityAttributeTypeDirectEditFeature(IFeatureProvider fp)
	{
		super(fp, EntityAttribute.class, MyAdminDslPackage.Literals.ENTITY_ATTRIBUTE__NAME);
	}

	@Override
	public int getEditingType()
	{
		return TYPE_CUSTOM;
	}

	public CellEditor createCellEditor(Composite parent)
	{
		return null;
	}
}