package de.pellepelster.myadmin.demo.client.modules;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.web.module.BaseModuleFactory;

public class TestModule1Factory extends BaseModuleFactory
{

	/** {@inheritDoc} */
	@Override
	public void getNewInstance(String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		new TestModule1(moduleUrl, moduleCallback, parameters);
	}

	@Override
	public boolean supports(String moduleUrl)
	{
		return supports(moduleUrl, TestModule1.MODULE_ID);
	}
}