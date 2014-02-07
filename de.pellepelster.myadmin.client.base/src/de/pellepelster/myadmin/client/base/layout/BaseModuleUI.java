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
package de.pellepelster.myadmin.client.base.layout;

import de.pellepelster.myadmin.client.base.module.IModule;

/**
 * Basic module UI implementation
 * 
 * @author pelle
 * 
 */
public abstract class BaseModuleUI<ContainerType, ModuleType extends IModule> implements IModuleUI<ContainerType, ModuleType>
{

	private final ModuleType module;

	private final String uiModuleId;

	public BaseModuleUI(ModuleType module, String uiModuleId)
	{
		super();
		this.module = module;
		this.uiModuleId = uiModuleId;
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
		return this.module;
	}

	@Override
	public int getOrder()
	{
		return this.module.getOrder();
	}

}
