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

import com.google.gwt.user.client.ui.Panel;

import de.pellepelster.myadmin.client.base.layout.BaseModuleUI;
import de.pellepelster.myadmin.client.base.module.IModule;

/**
 * Basic module UI implementation
 * 
 * @author pelle
 * 
 */
public abstract class BaseGwtModuleUI<ModuleType extends IModule> extends BaseModuleUI<Panel, ModuleType>
{

	public BaseGwtModuleUI(ModuleType module, String uiModuleId)
	{
		super(module, uiModuleId);
	}

}
