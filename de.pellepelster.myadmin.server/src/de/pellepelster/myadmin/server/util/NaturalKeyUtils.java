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
package de.pellepelster.myadmin.server.util;

import java.util.HashMap;
import java.util.Map;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.NaturalKey;
import de.pellepelster.myadmin.server.validators.AnnotationIterator;

public class NaturalKeyUtils
{
	public static Map<String, Object> getNaturalKeys(IBaseVO vo)
	{
		Map<String, Object>  naturalKeys = new HashMap<String, Object>();
		
		for (IAttributeDescriptor<?> attributeDescriptor : new AnnotationIterator(vo.getClass(), NaturalKey.class))
		{
			naturalKeys.put(attributeDescriptor.getAttributeName(), vo.get(attributeDescriptor.getAttributeName()));
		}

		return naturalKeys;
	}
}
