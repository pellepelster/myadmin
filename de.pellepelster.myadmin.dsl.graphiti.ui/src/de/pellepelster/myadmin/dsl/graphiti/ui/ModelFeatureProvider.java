package de.pellepelster.myadmin.dsl.graphiti.ui;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.NoMoveFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.base.NoResizeFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.base.attribute.BaseAttributeAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.reference.ReferenceDatatypeAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.reference.ReferenceDatatypeCreateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.reference.ReferenceDatatypeDirectEditFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.reference.ReferenceDatatypeLayoutFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.reference.ReferenceDatatypeUpdateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text.TextDatatypeAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text.TextDatatypeCreateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text.TextDatatypeDirectEditFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text.TextDatatypeLayoutFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text.TextDatatypeUpdateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityCreateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityLayoutFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityNameDirectEditFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityUpdateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute.EntityAttributeAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute.EntityAttributeCreateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute.EntityAttributeLayoutFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute.EntityAttributeNameDirectEditFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute.EntityAttributeNameUpdateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute.EntityAttributeRemoveFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute.EntityAttributeTypeDirectEditFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.PictogramElementQuery;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.TextDatatype;

public class ModelFeatureProvider extends DefaultFeatureProvider
{
	// private static Logger LOG = Logger.getLogger(ModelFeatureProvider.class);

	public ModelFeatureProvider(IDiagramTypeProvider dtp)
	{
		super(dtp);
	}

	@Override
	public IAddFeature getAddFeature(IAddContext context)
	{
		if (context.getNewObject() instanceof Entity)
		{
			return new EntityAddFeature(this);
		}

		if (context.getNewObject() instanceof TextDatatype)
		{
			return new TextDatatypeAddFeature(this);
		}

		if (context.getNewObject() instanceof ReferenceDatatype)
		{
			return new ReferenceDatatypeAddFeature(this);
		}

		if (context.getNewObject() instanceof EntityAttribute)
		{
			return new EntityAttributeAddFeature(this);
		}

		return super.getAddFeature(context);
	}

	@Override
	public ICreateFeature[] getCreateFeatures()
	{
		return new ICreateFeature[] { new EntityCreateFeature(this), new TextDatatypeCreateFeature(this), new EntityAttributeCreateFeature(this),
				new ReferenceDatatypeCreateFeature(this) };
	}

	@Override
	public IResizeShapeFeature getResizeShapeFeature(IResizeShapeContext context)
	{
		PictogramElement pictogramElement = context.getPictogramElement();
		Object businessObject = getBusinessObjectForPictogramElement(pictogramElement);

		if (businessObject instanceof EntityAttribute)
		{
			return new NoResizeFeature(this);
		}

		return super.getResizeShapeFeature(context);
	}

	@Override
	public ILayoutFeature getLayoutFeature(ILayoutContext context)
	{
		PictogramElement pictogramElement = context.getPictogramElement();
		Object businessObject = getBusinessObjectForPictogramElement(pictogramElement);

		if (businessObject instanceof Entity)
		{
			return new EntityLayoutFeature(this);
		}

		if (businessObject instanceof TextDatatype)
		{
			return new TextDatatypeLayoutFeature(this);
		}

		if (businessObject instanceof ReferenceDatatype)
		{
			return new ReferenceDatatypeLayoutFeature(this);
		}

		if (businessObject instanceof EntityAttribute)
		{
			return new EntityAttributeLayoutFeature(this);
		}

		return super.getLayoutFeature(context);
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IDirectEditingContext context)
	{
		PictogramElement pictogramElement = context.getPictogramElement();
		Object businessObject = getBusinessObjectForPictogramElement(pictogramElement);

		if (businessObject instanceof Entity)
		{
			return new EntityNameDirectEditFeature(this);
		}

		if (businessObject instanceof TextDatatype)
		{
			return new TextDatatypeDirectEditFeature(this);
		}

		if (businessObject instanceof ReferenceDatatype)
		{
			return new ReferenceDatatypeDirectEditFeature(this);
		}

		if (businessObject instanceof EntityAttribute)
		{
			if (PictogramElementQuery.create(context.getPictogramElement()).gaElementIdIs(BaseAttributeAddFeature.ATTRIBUTE_NAME_TEXT_ID).result())
			{
				return new EntityAttributeNameDirectEditFeature(this);
			}

			if (PictogramElementQuery.create(context.getPictogramElement()).gaElementIdIs(BaseAttributeAddFeature.ATTRIBUTE_TYPE_TEXT_ID).result())
			{
				return new EntityAttributeTypeDirectEditFeature(this);
			}

		}

		return super.getDirectEditingFeature(context);
	}

	@Override
	public IMoveShapeFeature getMoveShapeFeature(IMoveShapeContext context)
	{
		PictogramElement pictogramElement = context.getPictogramElement();
		Object businessObject = getBusinessObjectForPictogramElement(pictogramElement);

		if (businessObject instanceof EntityAttribute)
		{
			return new NoMoveFeature(this);
		}

		return super.getMoveShapeFeature(context);
	}

	@Override
	public IRemoveFeature getRemoveFeature(IRemoveContext context)
	{
		PictogramElement pictogramElement = context.getPictogramElement();
		Object businessObject = getBusinessObjectForPictogramElement(pictogramElement);

		if (businessObject instanceof EntityAttribute)
		{
			return new EntityAttributeRemoveFeature(this);
		}

		return super.getRemoveFeature(context);
	}

	@Override
	public IUpdateFeature getUpdateFeature(IUpdateContext context)
	{

		PictogramElement pictogramElement = context.getPictogramElement();

		if (pictogramElement instanceof ContainerShape)
		{

			Object bo = getBusinessObjectForPictogramElement(pictogramElement);

			if (bo instanceof Entity)
			{
				return new EntityUpdateFeature(this);
			}

			if (bo instanceof TextDatatype)
			{
				return new TextDatatypeUpdateFeature(this);
			}

			if (bo instanceof ReferenceDatatype)
			{
				return new ReferenceDatatypeUpdateFeature(this);
			}

			if (bo instanceof EntityAttribute)
			{
				return new EntityAttributeNameUpdateFeature(this);
			}
		}
		return super.getUpdateFeature(context);
	}
}
