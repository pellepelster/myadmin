package de.pellepelster.myadmin.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;

import de.pellepelster.myadmin.client.gwt.GWTLayoutFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.search.DictionarySearchQueryModuleUI;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.hierarchical.HierarchicalTreeModule;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;
import de.pellepelster.myadmin.demo.client.web.DemoClientConfiguration;
import de.pellepelster.myadmin.demo.client.web.navigation.DemoNavigationTree;

public class Demo implements EntryPoint
{
	private static final DemoResources RESOURCES = GWT.create(DemoResources.class);

	/** {@inheritDoc} */
	@Override
	public void onModuleLoad()
	{
		GWTLayoutFactory gwtLayoutFactory = new GWTLayoutFactory(Unit.PX);
		MyAdmin.getInstance().setLayoutFactory(gwtLayoutFactory);

		MyAdmin.getInstance().startModule(HierarchicalTreeModule.MODULE_ID, Direction.WEST.toString(),
				HierarchicalTreeModule.getParameterMap(TestClientHierarchicalConfiguration.ID, false));

		MyAdmin.getInstance().startModule(ModuleNavigationModule.MODULE_ID, Direction.WEST.toString());

		MyAdmin.getInstance().startModule(DictionarySearchQueryModuleUI.MODULE_LOCATOR, Direction.WEST.toString());

		init();
	}

	public void init()
	{
		DemoClientConfiguration.registerAll();

		DemoNavigationTree.ROOT.MASTERDATA.ADRESS.setImageResource(RESOURCES.address());

		// HierarchicalHookRegistry.getInstance().addActivationHook(TestClientHierarchicalConfiguration.ID,
		// new BaseActivationHook()
		// {
		// @Override
		// public void onActivate(DictionaryHierarchicalNodeVO
		// hierarchicalNodeVO)
		// {
		// if (hierarchicalNodeVO.getVoId() != null)
		// {
		// ModuleHandler.getInstance().startModule(TestModule1.MODULE_ID,
		// TestModule1.getParameterMap(hierarchicalNodeVO.getDictionaryName(),
		// hierarchicalNodeVO.getVoId()));
		// }
		// else
		// {
		// super.onActivate(hierarchicalNodeVO);
		// }
		// }
		// });
		// ModuleFactoryRegistry.getInstance().addModuleFactory(TestModule1.MODULE_ID,
		// new TestModule1Factory());
		// ModuleUIFactoryRegistry.getInstance().addModuleFactory(TestModule1.class,
		// new TestModule1UIFactory());
	}
}
