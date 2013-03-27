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
package de.pellepelster.myadmin.server.services;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import de.pellepelster.myadmin.db.copy.BaseCopyBean;
import de.pellepelster.myadmin.db.copy.handler.LongToBigIntegerCopyHandler;
import de.pellepelster.myadmin.db.copy.handler.TypeEqualsCopyHandler;

@Component
public class SimpleVOToXmlCopyBean extends BaseCopyBean implements InitializingBean
{
	public SimpleVOToXmlCopyBean()
	{
		super();
	}

	/** {@inheritDoc} */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		addFieldCopyHandler(new TypeEqualsCopyHandler());
		addFieldCopyHandler(new LongToBigIntegerCopyHandler());
	}

}
