/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package de.pellepelster.myadmin.client.gwt.modules.dictionary;

import de.pellepelster.myadmin.client.base.module.BaseModule;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.gwt.modules.IGwtModuleUI;

/**
 * Basic module UI implementation
 * 
 * @author pelle
 * 
 */
public abstract class BaseModuleUI<ModuleType extends IModule> implements IGwtModuleUI<ModuleType>
{

	public static final String getModuleUrl(String moduleId, String uiModuleId)
	{
		return BaseModule.getBaseModuleUrl(moduleId) + "&" + UI_MODULE_ID_PARAMETER_NAME + "=" + uiModuleId;
	}

	private final ModuleType module;

	public BaseModuleUI(ModuleType module)
	{
		super();
		this.module = module;
	}

	/** {@inheritDoc} */
	@Override
	public boolean close()
	{
		return true;
	}

	@Override
	public boolean contributesToBreadCrumbs()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public ModuleType getModule()
	{
		return module;
	}

	@Override
	public int getOrder()
	{
		return module.getOrder();
	}

}
