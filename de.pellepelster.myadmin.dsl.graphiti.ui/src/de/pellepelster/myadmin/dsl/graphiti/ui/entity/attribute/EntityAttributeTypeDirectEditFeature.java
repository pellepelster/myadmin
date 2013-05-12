package de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute;

import org.eclipse.draw2d.IFigure;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.ui.platform.ICellEditorProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.TypeCellEditor;
import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseDirectEditFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;
import de.pellepelster.myadmin.dsl.ui.ModelUiUtil;

public class EntityAttributeTypeDirectEditFeature extends BaseDirectEditFeature<EntityAttribute> implements ICellEditorProvider
{
	public EntityAttributeTypeDirectEditFeature(IFeatureProvider fp)
	{
		super(fp, EntityAttribute.class, MyAdminDslPackage.Literals.ENTITY_ATTRIBUTE__TYPE);
	}

	@Override
	public int getEditingType()
	{
		return TYPE_CUSTOM;
	}

	@Override
	public CellEditor createCellEditor(Composite parent)
	{
		return new TypeCellEditor(parent, getFeatureProvider());
	}

	@Override
	public void relocate(CellEditor cellEditor, IFigure figure)
	{
		cellEditor.getControl().setBounds(figure.getBounds().x, figure.getBounds().y, figure.getBounds().width, figure.getBounds().height);
	}

	@Override
	protected String inititalValueToString(Object Value)
	{
		return ModelUiUtil.getLabelProvider().getText(Value);
	}

	@Override
	public void setValue(String value, IDirectEditingContext context)
	{
		Object value1 = TypeCellEditor.CURRENT_VALUE;

		value1.toString();
	}

}