package de.pellepelster.myadmin.dsl.graphiti.ui.query;

import org.apache.log4j.Logger;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.pellepelster.myadmin.dsl.graphiti.ui.MyAdminGraphitiConstants;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.GraphitiProperties;

public abstract class BaseContextQuery<T extends IContext, Q> extends BaseQuery
{
	private static Logger LOG = Logger.getLogger(BaseContextQuery.class);

	private T context;

	protected BaseContextQuery(T context)
	{
		super();
		this.context = context;
	}

	public T getContext()
	{
		return this.context;
	}

	protected abstract Q getContextQuery();

	protected Q peElementIdIs(PictogramElement pe, String elementId)
	{
		getResults().add(elementId.equals(GraphitiProperties.get(pe, MyAdminGraphitiConstants.ELEMENT_ID_KEY)));
		return getContextQuery();
	}

}
