package de.pellepelster.myadmin.client.base.module;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseModule implements IModule
{
	private final AsyncCallback<IModule> moduleCallback;

	protected final Map<String, Object> parameters;

	public static final String MODULE_COUNTER_PARAMETER_ID = "moduleCounter";

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

	@Override
	public int getOrder()
	{
		if (this.parameters.containsKey(MODULE_COUNTER_PARAMETER_ID))
		{
			return Integer.parseInt(this.parameters.get(MODULE_COUNTER_PARAMETER_ID).toString());
		}
		else
		{
			throw new RuntimeException("module parameter '" + MODULE_COUNTER_PARAMETER_ID + "' not found");
		}

	}
}
