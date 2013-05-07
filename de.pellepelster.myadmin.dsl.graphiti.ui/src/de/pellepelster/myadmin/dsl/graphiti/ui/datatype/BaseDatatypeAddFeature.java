package de.pellepelster.myadmin.dsl.graphiti.ui.datatype;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.BaseClassAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.AddContextQuery;
import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.TextDatatype;

public abstract class BaseDatatypeAddFeature<T extends Datatype> extends BaseClassAddFeature<T>
{

	public BaseDatatypeAddFeature(IFeatureProvider fp, Class<T> datatypeClass)
	{
		super(fp, datatypeClass);
	}

	@Override
	public boolean canAdd(IAddContext context)
	{
		return AddContextQuery.create(context).newObjectTypeIs(TextDatatype.class).targetContainerTypeIs(Diagram.class).result();
	}

	@Override
	public PictogramElement add(IAddContext context)
	{
		return addInternal(context);
	}

	@Override
	protected String getNameText(Datatype businessObject)
	{
		return businessObject.getName();
	}

}