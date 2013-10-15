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
package de.pellepelster.myadmin.client.web.modules.navigation;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.util.BaseAsyncCallback;

public class ModuleNavigationModule extends de.pellepelster.myadmin.client.web.modules.BaseModuleNavigationModule
{

	public ModuleNavigationModule(ModuleVO moduleVO, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(moduleVO, moduleCallback, parameters);

		getModuleCallback().onSuccess(this);
	}

	public void getNavigationTreeContent(final AsyncCallback<NavigationTreeElements> asyncCallback)
	{
		GenericFilterVO<ModuleNavigationVO> genericFilterVO = new GenericFilterVO<ModuleNavigationVO>(ModuleNavigationVO.class);
		genericFilterVO.addCriteria(ModuleNavigationVO.FIELD_PARENT.getAttributeName(), null);
		genericFilterVO.addAssociation(ModuleNavigationVO.FIELD_CHILDREN.getAttributeName());
		genericFilterVO.addAssociation(ModuleNavigationVO.FIELD_MODULE).addAssociation(ModuleVO.FIELD_MODULEDEFINITION);

		MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService()
				.filter(genericFilterVO, new BaseAsyncCallback<List<ModuleNavigationVO>>(asyncCallback)
				{
					@Override
					public void onSuccess(List<ModuleNavigationVO> moduleNavigationVOs)
					{
						NavigationTreeElements root = new NavigationTreeElements(moduleNavigationVOs);
						asyncCallback.onSuccess(root);
					}
				});

	}
}
