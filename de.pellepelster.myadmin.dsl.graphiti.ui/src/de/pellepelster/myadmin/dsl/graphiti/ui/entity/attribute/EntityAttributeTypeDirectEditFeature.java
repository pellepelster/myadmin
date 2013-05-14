package de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute;

import org.eclipse.draw2d.IFigure;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.ui.platform.ICellEditorProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.TypeCellEditor;
import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseDirectEditFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.DatatypeType;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslFactory;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;
import de.pellepelster.myadmin.dsl.myAdminDsl.SimpleType;
import de.pellepelster.myadmin.dsl.myAdminDsl.SimpleTypes;
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

	private void updateEntityAttributeType(EntityAttribute entityAttribute, Object typeObject)
	{
		if (typeObject instanceof SimpleTypes)
		{
			updateEntityAttributeType(entityAttribute, (SimpleTypes) typeObject);
		}
		else if (typeObject instanceof Datatype)
		{
			Datatype datatype = (Datatype) typeObject;
			updateEntityAttributeType(entityAttribute, datatype);
		}
		else
		{
			throw new RuntimeException(String.format("updateEntityAttributeType not implemented for '%s'", typeObject.getClass().getName()));
		}
	}

	private void updateEntityAttributeType(EntityAttribute entityAttribute, SimpleTypes simpletypes)
	{
		SimpleType simpleType = MyAdminDslFactory.eINSTANCE.createSimpleType();
		simpleType.setType(simpletypes);

		entityAttribute.setType(simpleType);
	}

	private void updateEntityAttributeType(EntityAttribute entityAttribute, Datatype datatype)
	{
		DatatypeType datatypeType = MyAdminDslFactory.eINSTANCE.createDatatypeType();
		datatypeType.setType(datatype);

		entityAttribute.setType(datatypeType);
	}

	@Override
	public void setValue(String value, IDirectEditingContext context)
	{
		EntityAttribute entityAttribute = getBusinessObject(context);

		if (TypeCellEditor.CURRENT_VALUE.containsKey(value))
		{
			Object typeValue = TypeCellEditor.CURRENT_VALUE.get(value);
			TypeCellEditor.CURRENT_VALUE.remove(value);

			updateEntityAttributeType(entityAttribute, typeValue);
		}
		else
		{
			throw new RuntimeException(String.format(" no value found for key '%s'", String.valueOf(value)));
		}

	}

}