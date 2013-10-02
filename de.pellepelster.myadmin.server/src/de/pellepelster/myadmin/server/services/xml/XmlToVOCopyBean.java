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
package de.pellepelster.myadmin.server.services.xml;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.db.copy.BaseCopyBean;
import de.pellepelster.myadmin.db.copy.handler.BigIntegerToLongCopyHandler;
import de.pellepelster.myadmin.db.copy.handler.TypeEqualsCopyHandler;

@Component
public class XmlToVOCopyBean extends BaseCopyBean implements InitializingBean
{
	@Autowired
	private IBaseEntityService baseEntityService;
	
	public XmlToVOCopyBean()
	{
		super();
	}

	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		addFieldCopyHandler(new TypeEqualsCopyHandler());
		addFieldCopyHandler(new BigIntegerToLongCopyHandler());
		addFieldCopyHandler(new XmlReferenceCopyHandler(baseEntityService));
	}
}
