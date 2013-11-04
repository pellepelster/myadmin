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
package de.pellepelster.myadmin.client.gwt.modules.navigation;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseModuleUI;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.module.ModuleHandler;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;
import de.pellepelster.myadmin.client.web.modules.navigation.NavigationTreeElements;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class NavigationModuleUI extends BaseModuleUI<ModuleNavigationModule>
{

	private static class NavigationTreeModel implements TreeViewModel
	{

		private final List<NavigationTreeElements> navigationTree = new ArrayList<NavigationTreeElements>();

		private final SingleSelectionModel<NavigationTreeElements> selectionModel = new SingleSelectionModel<NavigationTreeElements>();
		private ListDataProvider<NavigationTreeElements> rootDataProvider;

		public NavigationTreeModel()
		{
			selectionModel.addSelectionChangeHandler(new Handler()
			{
				/** {@inheritDoc} */
				@Override
				public void onSelectionChange(SelectionChangeEvent event)
				{
					if (selectionModel.getSelectedObject() != null && selectionModel.getSelectedObject().hasModule())
					{
						ModuleHandler.getInstance().startModule(selectionModel.getSelectedObject().getModuleName());
					}

				}
			});
		}

		private Cell<NavigationTreeElements> getCell()
		{

			Cell<NavigationTreeElements> cell = new AbstractCell<NavigationTreeElements>()
			{
				/** {@inheritDoc} */
				@Override
				public void render(Context context, NavigationTreeElements value, SafeHtmlBuilder sb)
				{
					if (value != null)
					{
						sb.appendEscaped(value.getTitle());
					}
				}
			};

			return cell;
		}

		/**
		 * Get the {@link NodeInfo} that provides the children of the specified
		 * value.
		 */
		@Override
		public <T> NodeInfo<?> getNodeInfo(T value)
		{
			if (value == null)
			{
				rootDataProvider = new ListDataProvider<NavigationTreeElements>(navigationTree);
				return new DefaultNodeInfo<NavigationTreeElements>(rootDataProvider, getCell(), selectionModel, null);

			}
			else if (value instanceof NavigationTreeElements)
			{
				ListDataProvider<NavigationTreeElements> dataProvider = new ListDataProvider<NavigationTreeElements>(
						((NavigationTreeElements) value).getChildren());
				return new DefaultNodeInfo<NavigationTreeElements>(dataProvider, getCell(), selectionModel, null);
			}

			return null;
		}

		/** {@inheritDoc} */
		@Override
		public boolean isLeaf(Object value)
		{
			return (value instanceof NavigationTreeElements && ((NavigationTreeElements) value).getChildren().isEmpty());
		}

		public void setNavigationTreeModel(NavigationTreeElements navigationTreeElements)
		{
			if (rootDataProvider != null)
			{
				rootDataProvider.getList().clear();
				rootDataProvider.getList().addAll(navigationTreeElements.getChildren());
			}
		}

	}

	private final VerticalPanel verticalPanel;

	/**
	 * @param module
	 */
	public NavigationModuleUI(ModuleNavigationModule module)
	{
		super(module);

		verticalPanel = new VerticalPanel();
		verticalPanel.ensureDebugId(ModuleNavigationModule.MODULE_ID);

		final NavigationTreeModel navigationTreeModel = new NavigationTreeModel();
		CellTree cellTree = new CellTree(navigationTreeModel, null);
		verticalPanel.add(cellTree);

		module.getNavigationTreeContent(new AsyncCallback<NavigationTreeElements>()
		{
			@Override
			public void onFailure(Throwable caught)
			{
				throw new RuntimeException("error refreshing navigation tree", caught);
			}

			@Override
			public void onSuccess(NavigationTreeElements result)
			{
				navigationTreeModel.setNavigationTreeModel(result);
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer()
	{
		return verticalPanel;
	}

	@Override
	public String getTitle()
	{
		return MyAdmin.MESSAGES.navigationTitle();
	}

}
