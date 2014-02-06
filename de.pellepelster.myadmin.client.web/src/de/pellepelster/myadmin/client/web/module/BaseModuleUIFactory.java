package de.pellepelster.myadmin.client.web.module;

import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.module.ModuleUtils;

public abstract class BaseModuleUIFactory<ContainerType, ModuleType> implements IModuleUIFactory<ContainerType, ModuleType>
{

	protected boolean supports(String moduleUrl, String uiModuleId)
	{
		return (uiModuleId.equals(ModuleUtils.getUrlParameter(moduleUrl, IModuleUI.UI_MODULE_ID_PARAMETER_NAME)));
	}

}
