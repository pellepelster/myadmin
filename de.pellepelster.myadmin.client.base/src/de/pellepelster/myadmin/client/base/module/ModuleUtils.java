package de.pellepelster.myadmin.client.base.module;

import java.util.Map;

import com.google.common.base.Splitter;

public abstract class ModuleUtils
{

	public static boolean urlContainsModuleId(String moduleUrl, String moduleId)
	{
		return moduleId.equals(getModuleId(moduleUrl));
	}

	public static String getModuleId(String moduleUrl)
	{
		return getUrlParameter(moduleUrl, IModule.MODULE_ID_PARAMETER_NAME);
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