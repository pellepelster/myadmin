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
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;
import de.pellepelster.myadmin.client.web.module.ModuleHandler;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class ModuleNavigationModuleUI extends BaseModuleUI<ModuleNavigationModule>
{

	private static class NavigationTreeModel implements TreeViewModel
	{

		private final List<ModuleNavigationVO> navigationTree = new ArrayList<ModuleNavigationVO>();
		private final SingleSelectionModel<ModuleNavigationVO> selectionModel = new SingleSelectionModel<ModuleNavigationVO>();
		private ListDataProvider<ModuleNavigationVO> rootDataProvider;

		public NavigationTreeModel()
		{
			selectionModel.addSelectionChangeHandler(new Handler()
			{

				/** {@inheritDoc} */
				@Override
				public void onSelectionChange(SelectionChangeEvent event)
				{
					if (selectionModel.getSelectedObject() != null && selectionModel.getSelectedObject().getModule() != null)
					{
						ModuleHandler.getInstance().startModule(selectionModel.getSelectedObject().getModule().getName());
					}

				}
			});
		}

		private Cell<ModuleNavigationVO> getCell()
		{

			Cell<ModuleNavigationVO> cell = new AbstractCell<ModuleNavigationVO>()
			{
				/** {@inheritDoc} */
				@Override
				public void render(Context context, ModuleNavigationVO value, SafeHtmlBuilder sb)
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
				rootDataProvider = new ListDataProvider<ModuleNavigationVO>(navigationTree);
				return new DefaultNodeInfo<ModuleNavigationVO>(rootDataProvider, getCell(), selectionModel, null);

			}
			else if (value instanceof ModuleNavigationVO)
			{
				ListDataProvider<ModuleNavigationVO> dataProvider = new ListDataProvider<ModuleNavigationVO>(((ModuleNavigationVO) value).getChildren());
				return new DefaultNodeInfo<ModuleNavigationVO>(dataProvider, getCell(), selectionModel, null);
			}

			return null;
		}

		/** {@inheritDoc} */
		@Override
		public boolean isLeaf(Object value)
		{
			return (value instanceof ModuleNavigationVO && ((ModuleNavigationVO) value).getChildren().isEmpty());
		}

		public void setNavigationTreeModel(List<ModuleNavigationVO> navigationTree)
		{
			if (rootDataProvider != null)
			{
				rootDataProvider.getList().clear();
				rootDataProvider.getList().addAll(navigationTree);
			}
		}

	}

	private final VerticalPanel verticalPanel;

	/**
	 * @param module
	 */
	public ModuleNavigationModuleUI(ModuleNavigationModule module)
	{
		super(module);

		verticalPanel = new VerticalPanel();
		verticalPanel.ensureDebugId(ModuleNavigationModule.MODULE_ID);

		final NavigationTreeModel navigationTreeModel = new NavigationTreeModel();
		CellTree cellTree = new CellTree(navigationTreeModel, null);
		verticalPanel.add(cellTree);

		module.getNavigationTreeContent(new AsyncCallback<List<ModuleNavigationVO>>()
		{
			@Override
			public void onFailure(Throwable caught)
			{
				throw new RuntimeException("error refreshing navigation tree", caught);
			}

			@Override
			public void onSuccess(List<ModuleNavigationVO> result)
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
