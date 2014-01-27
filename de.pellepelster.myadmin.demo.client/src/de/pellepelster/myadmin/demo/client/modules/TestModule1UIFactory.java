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
package de.pellepelster.myadmin.demo.client.modules;

import java.util.Map;

import com.google.gwt.user.client.ui.Panel;

import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.web.module.IModuleUIFactory;

public class TestModule1UIFactory implements IModuleUIFactory<Panel, TestModule1>
{
	/** {@inheritDoc} */
	@Override
	public IModuleUI<Panel, TestModule1> getNewInstance(TestModule1 module, IModuleUI<?, ?> previousModuleUI, Map<String, Object> parameters)
	{
		return new TestModule1UI(module);
	}
}
