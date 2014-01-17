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

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.NaturalKey;
import de.pellepelster.myadmin.server.validators.AnnotationIterator;

public class NaturalKeyUtils
{
	public static List<IAttributeDescriptor<?>> getNaturalKeys(Class<? extends IBaseVO> voClass)
	{
		List<IAttributeDescriptor<?>> naturalKeys = new ArrayList<IAttributeDescriptor<?>>();

		for (IAttributeDescriptor<?> attributeDescriptor : new AnnotationIterator(voClass, NaturalKey.class))
		{
			naturalKeys.add(attributeDescriptor);
		}

		return naturalKeys;
	}
}
