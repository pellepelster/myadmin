package de.pellepelster.myadmin.client.base.module;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseModule implements IModule
{
	private final AsyncCallback<IModule> moduleCallback;

	protected final Map<String, Object> parameters;

	public static final String MODULE_COUNTER_PARAMETER_ID = "moduleCounter";

	public static final String MODULE_TITLE_PARAMETER_ID = "moduleTitle";

	protected AsyncCallback<IModule> getModuleCallback()
	{
		return this.moduleCallback;
	}

	public BaseModule(AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		this.moduleCallback = moduleCallback;
		this.parameters = parameters;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSingleton()
	{
		return true;
	}

	public Map<String, Object> getParameters()
	{
		return this.parameters;
	}

	public boolean hasParameter(String parameterName)
	{
		return this.parameters.containsKey(parameterName);
	}

	public Object getParameter(String parameterName)
	{
		return this.parameters.get(parameterName);
	}

	public String getStringParameter(String parameterName)
	{
		return this.parameters.get(parameterName).toString();
	}

	@Override
	public String getTitle()
	{
		if (hasParameter(MODULE_TITLE_PARAMETER_ID))
		{
			return this.parameters.get(MODULE_TITLE_PARAMETER_ID).toString();
		}
		else
		{
			return getModuleId();
		}
	}

	@Override
	public String getModuleId()
	{
		return this.toString();
	}

	@Override
	public int getOrder()
	{
		if (hasParameter(MODULE_COUNTER_PARAMETER_ID))
		{
			return Integer.parseInt(this.parameters.get(MODULE_COUNTER_PARAMETER_ID).toString());
		}
		else
		{
			throw new RuntimeException("module parameter '" + MODULE_COUNTER_PARAMETER_ID + "' not found");
		}

	}
}
