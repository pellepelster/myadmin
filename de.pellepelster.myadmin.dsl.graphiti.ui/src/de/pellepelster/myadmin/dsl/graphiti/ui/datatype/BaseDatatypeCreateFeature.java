package de.pellepelster.myadmin.dsl.graphiti.ui.datatype;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.BaseCreateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.CreateContextQuery;
import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;

public abstract class BaseDatatypeCreateFeature<BO extends Datatype> extends BaseCreateFeature<BO>
{
	public BaseDatatypeCreateFeature(IFeatureProvider fp, Class<BO> businessObjectClass, String name, String description)
	{
		super(fp, businessObjectClass, name, description);
	}

	@Override
	public boolean canCreate(ICreateContext context)
	{
		return CreateContextQuery.create(context).targetContainerTypeIs(Diagram.class).result();
	}

}