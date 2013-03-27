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
package de.pellepelster.myadmin.db.copy.handler;

import java.math.BigInteger;

import org.apache.commons.beanutils.PropertyUtils;

import de.pellepelster.myadmin.db.copy.FieldDescriptor;
import de.pellepelster.myadmin.db.copy.IFieldCopyHandler;

public class BigIntegerToLongCopyHandler implements IFieldCopyHandler
{

	@Override
	public boolean check(FieldDescriptor fieldDescriptor)
	{
		return BigInteger.class.isAssignableFrom(fieldDescriptor.getSourceType()) && long.class.isAssignableFrom(fieldDescriptor.getTargetType());
	}

	@Override
	public void copy(FieldDescriptor fieldDescriptor, Object sourceObject, Object targetObject) throws Exception
	{
		PropertyUtils.setProperty(targetObject, fieldDescriptor.getFieldName(), Long.parseLong(((BigInteger) fieldDescriptor.getSourceValue()).toString()));
	}

}
