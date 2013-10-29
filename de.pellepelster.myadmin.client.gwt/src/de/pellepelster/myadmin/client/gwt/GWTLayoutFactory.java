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
package de.pellepelster.myadmin.client.gwt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.layout.ILayoutFactory;
import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.gwt.modules.IGwtModuleUI;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.editor.DictionaryEditorModuleUIFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.search.DictionarySearchModuleUIFactory;
import de.pellepelster.myadmin.client.gwt.modules.hierarchical.HierarchicalTreeModuleUIFactory;
import de.pellepelster.myadmin.client.gwt.modules.navigation.NavigationModuleUIFactory;
import de.pellepelster.myadmin.client.web.module.IModuleUIFactory;
import de.pellepelster.myadmin.client.web.module.ModuleUIFactoryRegistry;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModule;
import de.pellepelster.myadmin.client.web.modules.hierarchical.HierarchicalTreeModule;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;

/**
 * {@link ILayoutFactory} implementation for GWT
 * 
 * @author pelle
 * 
 */
public class GWTLayoutFactory implements ILayoutFactory<Panel, Widget>
{

	private class PanelLayoutInfo
	{
		private final int size;
		private final boolean supportsMultipleChildren;
		private final Widget widget;

		public PanelLayoutInfo(int size, boolean supportsMultipleChildren, Widget widget)
		{
			super();
			this.size = size;
			this.supportsMultipleChildren = supportsMultipleChildren;
			this.widget = widget;
		}

		public Panel getPanel()
		{
			if (widget instanceof Panel)
			{
				return (Panel) widget;
			}
			else
			{
				return null;
			}
		}

		public StackLayoutPanel getStackLayoutPanel()
		{
			if (widget instanceof StackLayoutPanel)
			{
				return (StackLayoutPanel) widget;
			}
			else
			{
				return null;
			}
		}

		public boolean isSupportsMultipleChildren()
		{
			return supportsMultipleChildren;
		}
	}

	private final Map<Direction, PanelLayoutInfo> panels = new HashMap<DockLayoutPanel.Direction, PanelLayoutInfo>();

	private final LinkedHashMap<Direction, List<IGwtModuleUI<?>>> currentModules = new LinkedHashMap<Direction, List<IGwtModuleUI<?>>>();

	private final DockLayoutPanel rootPanel;

	/**
	 * Constructor for {@link GWTLayoutFactory}
	 * 
	 * @param unit
	 */
	public GWTLayoutFactory(Unit unit)
	{
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(ModuleNavigationModule.class, new NavigationModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(DictionarySearchModule.class, new DictionarySearchModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(DictionaryEditorModule.class, new DictionaryEditorModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(HierarchicalTreeModule.class, new HierarchicalTreeModuleUIFactory());

		rootPanel = new DockLayoutPanel(Unit.PCT);
		rootPanel.setWidth("100%");
		rootPanel.setHeight("100%");

		initializePanelLayout(Direction.WEST, 15, true);
		initializePanelLayout(Direction.CENTER, 85, false);

		RootLayoutPanel.get().add(rootPanel);
	}

	private void addModule(IGwtModuleUI<?> moduleUI, DockLayoutPanel.Direction direction)
	{
		if (!currentModules.containsKey(direction))
		{
			currentModules.put(direction, new ArrayList<IGwtModuleUI<?>>());
		}

		if (!currentModules.get(direction).contains(moduleUI))
		{
			currentModules.get(direction).add(moduleUI);
		}

		PanelLayoutInfo panelLayoutInfo = panels.get(direction);

		addToLayout(panelLayoutInfo, moduleUI.getContainer(), moduleUI.getTitle());
	}

	private void addToLayout(PanelLayoutInfo panelLayoutInfo, Panel newPanel, String title)
	{
		if (panelLayoutInfo.getStackLayoutPanel() != null)
		{
			newPanel.setWidth("100%");

			HTML html = new HTML(title);
			panelLayoutInfo.getStackLayoutPanel().add(newPanel, html, 3);

		}
		else if (panelLayoutInfo.getPanel() != null)
		{
			Panel panel = panelLayoutInfo.getPanel();
			removeAllChildren(panel);
			panel.add(newPanel);
		}

	}

	@Override
	public boolean closeAndBack(String location)
	{

		DockLayoutPanel.Direction direction = getDirection(location);

		if (closeCurrentModule(direction))
		{
			IGwtModuleUI<?> moduleUI = getCurrentModuleUI(direction);

			if (moduleUI != null)
			{
				addModule(moduleUI, direction);
			}

			return true;
		}
		else
		{
			return false;
		}

	}

	private boolean closeCurrentModule(DockLayoutPanel.Direction direction)
	{
		IGwtModuleUI<?> currentModuleUI = getCurrentModuleUI(direction);

		if (currentModuleUI != null && currentModuleUI.close())
		{
			currentModules.get(direction).remove(currentModuleUI);

			PanelLayoutInfo panelLayoutInfo = panels.get(direction);

			removeAllChildren(panelLayoutInfo.getPanel());

			return true;
		}
		else
		{
			return false;
		}
	}

	private IGwtModuleUI<?> getCurrentModuleUI(Direction direction)
	{
		for (Map.Entry<Direction, List<IGwtModuleUI<?>>> entry : currentModules.entrySet())
		{
			if (entry.getKey() == direction)
			{
				if (entry.getValue().isEmpty())
				{
					return null;
				}
				else
				{
					return entry.getValue().get(entry.getValue().size() - 1);
				}
			}
		}

		return null;
	}

	private Direction getDirection(String location)
	{
		try
		{
			return Direction.valueOf(location);
		}
		catch (Exception e)
		{
			return Direction.CENTER;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasBreadCrumbs(String location)
	{
		Direction direction = getDirection(location);

		return !currentModules.get(direction).isEmpty();
	}

	private void hideCurrentModule(DockLayoutPanel.Direction direction)
	{
		if (panels.containsKey(direction))
		{
			PanelLayoutInfo panelLayoutInfo = panels.get(direction);
			removeAllChildren(panelLayoutInfo.getPanel());
		}
		else
		{
			throw new RuntimeException("no panel layout info found for direction '" + direction + "'");
		}
	}

	private void initializePanelLayout(Direction direction, int size, boolean supportsMultipleChildren)
	{
		Widget panel = null;

		if (supportsMultipleChildren)
		{
			panel = new StackLayoutPanel(Unit.EM);
			panel.setHeight("100%");
		}
		else
		{
			panel = new HorizontalPanel();
		}

		panel.setHeight("100%");
		panel.setWidth("100%");

		panel.setStyleName("docklayoutpanel-" + direction.toString().toLowerCase(), true);

		panels.put(direction, new PanelLayoutInfo(size, supportsMultipleChildren, panel));

		switch (direction)
		{
			case CENTER:
				rootPanel.add(panel);
				break;
			case EAST:
				rootPanel.addEast(panel, size);
				break;
			case NORTH:
				rootPanel.addNorth(panel, size);
				break;
			case SOUTH:
				rootPanel.addSouth(panel, size);
				break;
			case WEST:
				rootPanel.addWest(panel, size);
				break;
			case LINE_END:
				rootPanel.addLineEnd(panel, size);
				break;
			case LINE_START:
				rootPanel.addLineStart(panel, size);
				break;
			default:
				throw new RuntimeException("unsupported direction '" + direction.toString() + "'");
		}
	}

	private void removeAllChildren(Panel panel)
	{
		Iterator<Widget> childrenIterator = panel.iterator();

		while (childrenIterator.hasNext())
		{
			panel.remove(childrenIterator.next());
		}
	}

	private void showModule(IGwtModuleUI<?> moduleUI, DockLayoutPanel.Direction direction)
	{
		if (panels.containsKey(direction))
		{
			IGwtModuleUI<?> currentModuleUI = getCurrentModuleUI(direction);
			PanelLayoutInfo panelLayoutInfo = panels.get(direction);

			if (currentModuleUI != null && !panelLayoutInfo.isSupportsMultipleChildren())
			{
				if (currentModuleUI.contributesToBreadCrumbs())
				{
					hideCurrentModule(direction);
				}
				else if (!closeCurrentModule(direction))
				{
					return;
				}
			}
		}
		else
		{
			throw new RuntimeException("no panel layout info found for direction '" + direction + "'");
		}

		addModule(moduleUI, direction);

	}

	/** {@inheritDoc} */
	@Override
	public void showModuleUI(@SuppressWarnings("rawtypes") IModuleUI moduleUI)
	{
		showModule((IGwtModuleUI<?>) moduleUI, Direction.CENTER);
	}

	/** {@inheritDoc} */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void startModuleUI(IModule module, String location, Map<String, Object> parameters)
	{
		DockLayoutPanel.Direction direction = getDirection(location);

		IGwtModuleUI moduleUI = null;

		IModuleUIFactory moduleUIFactory = ModuleUIFactoryRegistry.getInstance().getModuleFactory(module.getClass());
		moduleUI = (IGwtModuleUI) moduleUIFactory.getNewInstance(module, getCurrentModuleUI(direction), parameters);

		showModule(moduleUI, direction);
	}
}
