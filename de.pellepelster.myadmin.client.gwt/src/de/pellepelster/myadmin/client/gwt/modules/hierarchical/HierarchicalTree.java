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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.pellepelster.myadmin.client.base.modules.hierarchical.HierarchicalConfiguration;

public class HierarchicalTree extends VerticalPanel
{
	private HierarchicalConfiguration hierarchicalConfiguration;

	public HierarchicalTree(HierarchicalConfiguration hierarchicalConfiguration)
	{
		this.hierarchicalConfiguration = hierarchicalConfiguration;

		CellTree.Resources treeResources = GWT.create(CellTree.BasicResources.class);

		HierarchicalTreeModel hierarchicalTreeModel = new HierarchicalTreeModel(hierarchicalConfiguration);
		CellTree cellTree = new CellTree(hierarchicalTreeModel, null, treeResources);
		add(cellTree);
	}

}
