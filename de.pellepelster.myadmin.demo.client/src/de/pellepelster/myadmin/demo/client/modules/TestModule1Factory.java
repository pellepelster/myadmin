package de.pellepelster.myadmin.demo.client.modules;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.module.IModuleFactory;

public class TestModule1Factory implements IModuleFactory
{

	/** {@inheritDoc} */
	@Override
	public void getNewInstance(ModuleVO moduleVO, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		new TestModule1(moduleVO, moduleCallback, parameters);
	}

}