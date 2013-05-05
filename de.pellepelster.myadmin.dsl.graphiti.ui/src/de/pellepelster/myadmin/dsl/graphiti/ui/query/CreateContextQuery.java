package de.pellepelster.myadmin.dsl.graphiti.ui.query;

import org.eclipse.graphiti.features.context.ICreateContext;

public class CreateContextQuery extends BaseContextQuery<ICreateContext, CreateContextQuery>
{
	public CreateContextQuery(ICreateContext context)
	{
		super(context);
	}

	public static CreateContextQuery create(ICreateContext context)
	{
		return new CreateContextQuery(context);
	}

	@Override
	protected CreateContextQuery getContextQuery()
	{
		return this;
	}

}
