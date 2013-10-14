package de.pellepelster.myadmin.demo.client.test;

import com.google.gwt.core.client.EntryPoint;

import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.test.JunitLayoutFactory;

public class DemoTest implements EntryPoint {

	@Override
	public void onModuleLoad() {

		// MyAdminGWTRemoteServiceLocator.getInstance().setRemoteBaseUrl("http://localhost:8180/de.pellepelster.myadmin.demo/remote/rpc");
		MyAdmin.getInstance().setLayoutFactory(new JunitLayoutFactory());

	}

}
