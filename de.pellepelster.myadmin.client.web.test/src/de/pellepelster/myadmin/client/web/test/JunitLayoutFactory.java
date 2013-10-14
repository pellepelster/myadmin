package de.pellepelster.myadmin.client.web.test;

import java.util.List;
import java.util.Map;

import de.pellepelster.myadmin.client.base.layout.IDictionaryLayoutStrategy;
import de.pellepelster.myadmin.client.base.layout.ILayoutFactory;
import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.web.module.ModuleUIFactoryRegistry;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;
import de.pellepelster.myadmin.client.web.test.modules.navigation.NavigationModuleTestUIFactory;

public class JunitLayoutFactory implements ILayoutFactory {

	public JunitLayoutFactory() {
		super();

		ModuleUIFactoryRegistry.getInstance().addModuleFactory(ModuleNavigationModule.class, new NavigationModuleTestUIFactory());

	}

	@Override
	public boolean closeAndBack(String location) {
		return false;
	}

	@Override
	public IDictionaryLayoutStrategy getLayoutStrategy(List observableValues, LAYOUT_TYPE layoutType) {
		return null;
	}

	@Override
	public boolean hasBreadCrumbs(String location) {
		return false;
	}

	@Override
	public void showModuleUI(IModuleUI moduleUI) {
		moduleUI.toString();
	}

	@Override
	public void startModuleUI(IModule module, String location, Map parameters) {
		module.toString();
	}

}
