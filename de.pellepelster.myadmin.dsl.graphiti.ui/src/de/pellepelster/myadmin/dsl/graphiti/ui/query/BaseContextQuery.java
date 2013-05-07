package de.pellepelster.myadmin.dsl.graphiti.ui.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.graphiti.features.context.ITargetContext;

import de.pellepelster.myadmin.dsl.graphiti.ui.MyAdminGraphitiConstants;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.GraphitiProperties;

public abstract class BaseContextQuery<T extends ITargetContext, Q>
{
	private static Logger LOG = Logger.getLogger(BaseContextQuery.class);

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

	public Q targetContainerTypeIs(Class<?> clazz)
	{
		this.results.add(clazz.isAssignableFrom(this.context.getTargetContainer().getClass()));
		return getContextQuery();
	}

	public Q targetContainerElementIdIs(String elementId)
	{
		LOG.error(String.format("target container: %s", this.context.getTargetContainer().eClass().getName(),
				GraphitiProperties.get(this.context.getTargetContainer(), MyAdminGraphitiConstants.ELEMENT_ID_KEY)));

		this.results.add(elementId.equals(GraphitiProperties.get(this.context.getTargetContainer(), MyAdminGraphitiConstants.ELEMENT_ID_KEY)));
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
