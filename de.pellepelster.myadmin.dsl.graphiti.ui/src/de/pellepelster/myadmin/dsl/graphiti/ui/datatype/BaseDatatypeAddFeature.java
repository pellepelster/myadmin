package de.pellepelster.myadmin.dsl.graphiti.ui.datatype;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerAddFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;

public abstract class BaseDatatypeAddFeature<T extends Datatype> extends BaseContainerAddFeature<T>
{

	public BaseDatatypeAddFeature(IFeatureProvider fp, Class<T> datatypeClass)
	{
		super(fp, datatypeClass);
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