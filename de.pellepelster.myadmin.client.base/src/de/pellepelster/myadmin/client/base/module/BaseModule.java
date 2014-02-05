package de.pellepelster.myadmin.client.base.module;

import java.util.Map;

import com.google.common.base.Splitter;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseModule implements IModule
{
	public static final String getBaseModuleUrl(String moduleId)
	{
		return MODULE_ID_PARAMETER_NAME + "=" + moduleId;
	}

	private final AsyncCallback<IModule> moduleCallback;

	protected final Map<String, Object> parameters;

	public static final String MODULE_COUNTER_PARAMETER_ID = "moduleCounter";

	public static final String MODULE_TITLE_PARAMETER_ID = "moduleTitle";

	private String moduleUrl;

	protected AsyncCallback<IModule> getModuleCallback()
	{
		return this.moduleCallback;
	}

	public BaseModule(String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		this.moduleUrl = moduleUrl;
		this.moduleCallback = moduleCallback;
		this.parameters = parameters;

		Map<String, String> urlSegments = Splitter.on("&").withKeyValueSeparator("=").split(moduleUrl);
		parameters.putAll(urlSegments);
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
			return getModuleUrl();
		}
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

	@Override
	public String getModuleUrl()
	{
		return this.moduleUrl;
	}

}
