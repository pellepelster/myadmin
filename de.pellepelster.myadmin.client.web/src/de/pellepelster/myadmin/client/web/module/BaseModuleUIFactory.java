package de.pellepelster.myadmin.client.web.module;

import de.pellepelster.myadmin.client.base.layout.IModuleUI;

public abstract class BaseModuleUIFactory<ContainerType, ModuleType> implements IModuleUIFactory<ContainerType, ModuleType>
{

	protected boolean supports(String moduleUrl, String moduleId)
	{
		return (moduleId.equals(BaseModuleFactory.getUrlParameter(moduleUrl, IModuleUI.UI_MODULE_ID_PARAMETER_NAME)));
	}

}
