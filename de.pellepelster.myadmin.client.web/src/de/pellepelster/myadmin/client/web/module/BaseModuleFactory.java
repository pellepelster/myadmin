package de.pellepelster.myadmin.client.web.module;

import java.util.Map;

import com.google.common.base.Splitter;

import de.pellepelster.myadmin.client.base.module.IModule;

public abstract class BaseModuleFactory implements IModuleFactory
{

	protected boolean supports(String moduleUrl, String moduleId)
	{
		return (moduleId.equals(getUrlParameter(moduleUrl, IModule.MODULE_ID_PARAMETER_NAME)));
	}

	public static String getUrlParameter(String moduleUrl, String parameterName)
	{
		Map<String, String> urlSegments = Splitter.on("&").withKeyValueSeparator("=").split(moduleUrl);
		return urlSegments.get(parameterName);
	}

	public static boolean hasUrlParameter(String moduleUrl, String parameterName)
	{
		return getUrlParameter(moduleUrl, parameterName) != null;
	}
}
