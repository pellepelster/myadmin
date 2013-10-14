package de.pellepelster.myadmin.client.web.test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;

import de.pellepelster.myadmin.client.base.layout.IDictionaryLayoutStrategy;
import de.pellepelster.myadmin.client.base.layout.ILayoutFactory;
import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.web.module.IModuleUIFactory;
import de.pellepelster.myadmin.client.web.module.ModuleUIFactoryRegistry;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;
import de.pellepelster.myadmin.client.web.test.modules.navigation.NavigationModuleTestUIFactory;

public class JunitLayoutFactory implements ILayoutFactory
{

	private Map<String, AsyncCallback<IModuleUI>> layoutFactoryCallbacks = new HashMap<String, AsyncCallback<IModuleUI>>();

	public interface JunitLayoutFactoryCallback
	{
		public void onStartModuleUI(IModuleUI moduleUI);

	}

	private final LinkedHashMap<Direction, List<IModuleUI>> currentModules = new LinkedHashMap<Direction, List<IModuleUI>>();

	public JunitLayoutFactory()
	{
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(ModuleNavigationModule.class, new NavigationModuleTestUIFactory());
	}

	@Override
	public boolean closeAndBack(String location)
	{
		return false;
	}

	@Override
	public IDictionaryLayoutStrategy getLayoutStrategy(List observableValues, LAYOUT_TYPE layoutType)
	{
		return null;
	}

	@Override
	public boolean hasBreadCrumbs(String location)
	{
		return false;
	}

	@Override
	public void showModuleUI(IModuleUI moduleUI)
	{
		moduleUI.toString();
	}

	private IModuleUI getCurrentModuleUI(Direction direction)
	{
		for (Map.Entry<Direction, List<IModuleUI>> entry : this.currentModules.entrySet())
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

	@Override
	public void startModuleUI(IModule module, String location, Map parameters)
	{
		DockLayoutPanel.Direction direction = getDirection(location);

		IModuleUI moduleUI = null;

		IModuleUIFactory moduleUIFactory = ModuleUIFactoryRegistry.getInstance().getModuleFactory(module.getClass());
		moduleUI = moduleUIFactory.getNewInstance(module, getCurrentModuleUI(direction), parameters);

		if (this.layoutFactoryCallbacks.containsKey(module.getModuleName()))
		{
			this.layoutFactoryCallbacks.get(module.getModuleName()).onSuccess(moduleUI);
		}
	}

	public void addLayoutCallback(String moduleName, AsyncCallback<IModuleUI> layoutFactoryCallback)
	{
		this.layoutFactoryCallbacks.put(moduleName, layoutFactoryCallback);
	}

}
