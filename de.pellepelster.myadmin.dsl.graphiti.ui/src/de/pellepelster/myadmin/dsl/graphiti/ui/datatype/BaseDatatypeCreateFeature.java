package de.pellepelster.myadmin.dsl.graphiti.ui.datatype;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.pellepelster.myadmin.dsl.graphiti.ui.query.CreateContextQuery;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.BaseClassCreateFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;

public abstract class BaseDatatypeCreateFeature<T extends Datatype> extends BaseClassCreateFeature
{
	public BaseDatatypeCreateFeature(IFeatureProvider fp, String name, String description)
	{
		super(fp, name, description);
	}

	@Override
	public boolean canCreate(ICreateContext context)
	{
		return CreateContextQuery.create(context).targetContainerIs(Diagram.class).result();
	}

}