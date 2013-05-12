package de.pellepelster.myadmin.dsl.graphiti.ui.query;

import org.apache.log4j.Logger;
import org.eclipse.graphiti.features.context.ITargetContext;

public abstract class BaseTargetContextQuery<T extends ITargetContext, Q> extends BaseContextQuery<T, Q>
{
	private static Logger LOG = Logger.getLogger(BaseTargetContextQuery.class);

	protected BaseTargetContextQuery(T context)
	{
		super(context);
	}

	public Q targetContainerTypeIs(Class<?> clazz)
	{
		getResults().add(clazz.isAssignableFrom(getContext().getTargetContainer().getClass()));
		return getContextQuery();
	}

	public Q targetContainerElementIdIs(String elementId)
	{
		return peElementIdIs(getContext().getTargetContainer(), elementId);
	}

	@Override
	protected abstract Q getContextQuery();

}
