package de.pellepelster.myadmin.dsl.graphiti.ui.query;

import org.eclipse.graphiti.features.context.IAddContext;

public class AddContextQuery extends BaseTargetContextQuery<IAddContext, AddContextQuery>
{
	public AddContextQuery(IAddContext context)
	{
		super(context);
	}

	public static AddContextQuery create(IAddContext context)
	{
		return new AddContextQuery(context);
	}

	public AddContextQuery newObjectTypeIs(Class<?> clazz)
	{
		getResults().add(clazz.isAssignableFrom(getContext().getNewObject().getClass()));
		return this;
	}

	@Override
	protected AddContextQuery getContextQuery()
	{
		return this;
	}

}
