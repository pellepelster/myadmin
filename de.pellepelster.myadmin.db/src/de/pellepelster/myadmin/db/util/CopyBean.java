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
package de.pellepelster.myadmin.db.util;

import de.pellepelster.myadmin.db.copy.BaseCopyBean;
import de.pellepelster.myadmin.db.copy.handler.EnumCopyHandler;
import de.pellepelster.myadmin.db.copy.handler.MapCopyHandler;
import de.pellepelster.myadmin.db.copy.handler.TypeEqualsCopyHandler;

/**
 * Provides deep bean copy
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public final class CopyBean extends BaseCopyBean
{

	private static CopyBean instance;

	public static CopyBean getInstance()
	{
		if (instance == null)
		{
			instance = new CopyBean();
		}

		return instance;
	}
	
	@Override
	protected Class<?> getMappedTargetType(Class<?> sourceType)
	{
		return EntityVOMapper.getInstance().getMappedClass(sourceType);
	}

	private CopyBean()
	{
		super();
		
		addFieldCopyHandler(new TypeEqualsCopyHandler());
		addFieldCopyHandler(new EnumCopyHandler());
		addFieldCopyHandler(new MapCopyHandler());
		
	}

}
