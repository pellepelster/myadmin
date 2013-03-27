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
package de.pellepelster.myadmin.demo.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.web.MyAdmin;

public class DictionaryServiceRemoteGWTTest extends GWTTestCase
{

	/** {@inheritDoc} */
	@Override
	public String getModuleName()
	{
		return "de.pellepelster.myadmin.demo.MyAdminDemo";
	}

	public void testDictionaryService()
	{
		delayTestFinish(2000);

		MyAdmin.getInstance().getRemoteServiceLocator().getDictionaryService().getDictionary("Country", new AsyncCallback<IDictionaryModel>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				caught.toString();
			}

			@Override
			public void onSuccess(IDictionaryModel result)
			{
				result.toString();

			}
		});

		// IBaseEntityServiceGWTAsync baseEntityService =
		// GWT.create(IBaseEntityServiceGWT.class);
		// ServiceDefTarget formEndpoint = (ServiceDefTarget) baseEntityService;
		// formEndpoint.setServiceEntryPoint(GWT.getModuleBaseURL() +
		// "MyAdminDemoRemote/rpc/BaseEntityService");
		//
		// GenericFilterVO genericFilterVO = new
		// GenericFilterVO(ModuleNavigationVO.class.getName());
		// genericFilterVO.addCriteria(ModuleNavigationVO.FIELD_PARENT, null);
		//
		// baseEntityService.filter(genericFilterVO, new
		// AsyncCallback<List<IBaseVO>>()
		// {
		//
		// @Override
		// public void onFailure(Throwable arg0)
		// {
		// fail(arg0.getMessage());
		// finishTest();
		// }
		//
		// @Override
		// public void onSuccess(List<IBaseVO> arg0)
		// {
		// finishTest();
		// }
		// });
	}
}
