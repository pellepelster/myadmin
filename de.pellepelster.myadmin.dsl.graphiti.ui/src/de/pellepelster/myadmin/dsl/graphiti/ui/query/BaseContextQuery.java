package de.pellepelster.myadmin.dsl.graphiti.ui.query;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.graphiti.features.context.ITargetContext;

public abstract class BaseContextQuery<T extends ITargetContext, Q>
{
	private T context;

	private List<Boolean> results = new ArrayList<>();

	protected BaseContextQuery(T context)
	{
		super();
		this.context = context;
	}

	public T getContext()
	{
		return this.context;
	}

	public Q targetContainerIs(Class<?> clazz)
	{
		this.results.add(clazz.isAssignableFrom(this.context.getTargetContainer().getClass()));
		return getContextQuery();
	}

	protected abstract Q getContextQuery();

	public List<Boolean> getResults()
	{
		return this.results;
	}

	public boolean result()
	{
		for (Boolean result : this.results)
		{
			if (!result)
			{
				return false;
			}
		}

		return true;
	}

}
