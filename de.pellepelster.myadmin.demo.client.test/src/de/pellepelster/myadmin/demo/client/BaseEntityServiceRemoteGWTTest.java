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

import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.demo.client.web.entities.Test1VO;

public class BaseEntityServiceRemoteGWTTest extends GWTTestCase
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

		final Test1VO test1VO = new Test1VO();
		test1VO.setTextDatatype1("abc");

		MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().create(test1VO, new AsyncCallback<Test1VO>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				fail(caught.getMessage());
				finishTest();
			}

			@Override
			public void onSuccess(Test1VO result)
			{
				assertEquals(test1VO.getTextDatatype1(), result.getTextDatatype1());
				finishTest();
			}
		});
	}
}
