package de.pellepelster.myadmin.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;

import de.pellepelster.myadmin.client.gwt.GWTLayoutFactory;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryHierarchicalNodeVO;
import de.pellepelster.myadmin.client.web.module.ModuleHandler;
import de.pellepelster.myadmin.client.web.modules.hierarchical.HierarchicalTreeModule;
import de.pellepelster.myadmin.client.web.modules.hierarchical.hooks.BaseActivationHook;
import de.pellepelster.myadmin.client.web.modules.hierarchical.hooks.HierarchicalHookRegistry;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;
import de.pellepelster.myadmin.demo.client.modules.TestModule1;

public class Demo implements EntryPoint
{
	/** {@inheritDoc} */
	@Override
	public void onModuleLoad()
	{
		GWTLayoutFactory gwtLayoutFactory = new GWTLayoutFactory(Unit.PX);
		MyAdmin.getInstance().setLayoutFactory(gwtLayoutFactory);

		MyAdmin.getInstance().startModule(ModuleNavigationModule.MODULE_ID, Direction.WEST.toString());
		MyAdmin.getInstance().startModule(HierarchicalTreeModule.MODULE_ID, Direction.WEST.toString(),
				HierarchicalTreeModule.getParameterMap(TestClientHierarchicalConfiguration.ID, false));

		// init();
	}

	public void init()
	{
		HierarchicalHookRegistry.getInstance().addActivationHook(TestClientHierarchicalConfiguration.ID, new BaseActivationHook()
		{
			@Override
			public void onActivate(DictionaryHierarchicalNodeVO hierarchicalNodeVO)
			{
				if (hierarchicalNodeVO.getVoId() != null)
				{
					ModuleHandler.getInstance().startModule(TestModule1.MODULE_ID,
							TestModule1.getParameterMap(hierarchicalNodeVO.getDictionaryName(), hierarchicalNodeVO.getVoId()));
				}
				else
				{
					super.onActivate(hierarchicalNodeVO);
				}
			}
		});
		// ModuleFactoryRegistry.getInstance().addModuleFactory(TestModule1.MODULE_ID,
		// new TestModule1Factory());
		// ModuleUIFactoryRegistry.getInstance().addModuleFactory(TestModule1.class,
		// new TestModule1UIFactory());
	}
}
