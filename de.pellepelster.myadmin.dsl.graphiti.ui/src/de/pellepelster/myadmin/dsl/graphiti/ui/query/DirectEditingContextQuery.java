package de.pellepelster.myadmin.dsl.graphiti.ui.query;

import org.eclipse.graphiti.features.context.IDirectEditingContext;

public class DirectEditingContextQuery extends BaseContextQuery<IDirectEditingContext, DirectEditingContextQuery>
{
	public DirectEditingContextQuery(IDirectEditingContext context)
	{
		super(context);
	}

	public static DirectEditingContextQuery create(IDirectEditingContext context)
	{
		return new DirectEditingContextQuery(context);
	}

	@Override
	protected DirectEditingContextQuery getContextQuery()
	{
		return this;
	}

	public DirectEditingContextQuery peElementIdIs(String elementId)
	{
		return peElementIdIs(getContext().getPictogramElement(), elementId);
	}
}
