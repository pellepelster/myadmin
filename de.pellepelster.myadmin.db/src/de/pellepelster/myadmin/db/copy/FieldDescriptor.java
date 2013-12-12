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
package de.pellepelster.myadmin.db.copy;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import com.google.common.base.Objects;

public class FieldDescriptor
{
	private final String fieldName;

	private final PropertyDescriptor sourcePropertyDescriptor;
	private final PropertyDescriptor targetPropertyDescriptor;

	private final Object sourceValue;
	private final Object targetValue;

	public FieldDescriptor(String fieldName, PropertyDescriptor sourcePropertyDescriptor, Object sourceValue, PropertyDescriptor targetPropertyDescriptor,
			Object targetValue)
	{
		super();
		this.fieldName = fieldName;

		this.sourcePropertyDescriptor = sourcePropertyDescriptor;
		this.sourceValue = sourceValue;

		this.targetPropertyDescriptor = targetPropertyDescriptor;
		this.targetValue = targetValue;
	}

	public String getFieldName()
	{
		return this.fieldName;
	}

	public PropertyDescriptor getSourcePropertyDescriptor()
	{
		return this.sourcePropertyDescriptor;
	}

	public PropertyDescriptor getTargetPropertyDescriptor()
	{
		return this.targetPropertyDescriptor;
	}

	public Object getTargetValue()
	{
		return this.targetValue;
	}

	public Object getSourceValue()
	{
		return this.sourceValue;
	}

	@SuppressWarnings("rawtypes")
	public Class getSourceType()
	{
		return this.sourcePropertyDescriptor.getPropertyType();
	}

	@SuppressWarnings("rawtypes")
	public Class getTargetType()
	{
		return this.targetPropertyDescriptor.getPropertyType();
	}

	public boolean sourceHasReadMethod()
	{
		return this.sourcePropertyDescriptor != null && this.sourcePropertyDescriptor.getReadMethod() != null;
	}

	public boolean targetHasWriteMethod()
	{
		return this.targetPropertyDescriptor != null && this.targetPropertyDescriptor.getWriteMethod() != null;
	}

	public Method getTargetWriteMethod()
	{
		return this.targetPropertyDescriptor.getWriteMethod();
	}

	public boolean targetHasReadMethod()
	{
		return this.targetPropertyDescriptor != null && this.targetPropertyDescriptor.getReadMethod() != null;
	}

	@Override
	public String toString()
	{
		return Objects.toStringHelper(this).add("fieldName", this.fieldName).toString();
	}

}
