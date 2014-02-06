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
package de.pellepelster.myadmin.client.gwt.modules.hierarchical;

import com.google.common.base.Optional;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseGwtModuleUI;
import de.pellepelster.myadmin.client.web.modules.hierarchical.HierarchicalTreeModule;

public class HierarchicalTreeModuleUI extends BaseGwtModuleUI<HierarchicalTreeModule>
{
	public static final String MODULE_ID = HierarchicalTreeModuleUI.class.getName();

	private final VerticalPanel verticalPanel;

	public HierarchicalTreeModuleUI(HierarchicalTreeModule module)
	{
		super(module, MODULE_ID);

		verticalPanel = new VerticalPanel();

		HierarchicalTree hierarchicalTree = new HierarchicalTree(module.getHierarchicalConfiguration(), Optional.fromNullable(getModule().getShowAddNodes())
				.or(true), getModule().getNodeActivatedHandler());
		verticalPanel.add(hierarchicalTree);
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer()
	{
		return verticalPanel;
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle()
	{
		return getModule().getHierarchicalConfiguration().getTitle();
	}

}