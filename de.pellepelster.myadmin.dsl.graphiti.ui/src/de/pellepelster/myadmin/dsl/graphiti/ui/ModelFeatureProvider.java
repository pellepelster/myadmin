package de.pellepelster.myadmin.dsl.graphiti.ui;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text.TextDatatypeAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text.TextDatatypeCreateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text.TextDatatypeDirectEditFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text.TextDatatypeLayoutFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text.TextDatatypeUpdateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityCreateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityDirectEditFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityLayoutFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.entity.EntityUpdateFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
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

		return super.getAddFeature(context);
	}

	@Override
	public ICreateFeature[] getCreateFeatures()
	{
		return new ICreateFeature[] { new EntityCreateFeature(this), new TextDatatypeCreateFeature(this) };
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

		return super.getLayoutFeature(context);
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IDirectEditingContext context)
	{
		PictogramElement pictogramElement = context.getPictogramElement();
		Object businessObject = getBusinessObjectForPictogramElement(pictogramElement);

		if (businessObject instanceof Entity)
		{
			return new EntityDirectEditFeature(this);
		}

		if (businessObject instanceof TextDatatype)
		{
			return new TextDatatypeDirectEditFeature(this);
		}

		return super.getDirectEditingFeature(context);
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
		}
		return super.getUpdateFeature(context);
	}
}
