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
package de.pellepelster.myadmin.client.web.test.vo;

import de.pellepelster.myadmin.client.base.db.vos.BaseVO;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public class Test3VO extends BaseVO implements IBaseVO
{

	private static final long serialVersionUID = -8497877063767382205L;

	private long id;

	public static final de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<String> FIELD_STRING3 = new de.pellepelster.myadmin.client.base.db.vos.AttributeDescriptor<String>(
			"string3", String.class, String.class);

	public static de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<?>[] getFieldDescriptors()
	{
		return new de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor[] {

		FIELD_ID, FIELD_STRING3

		};
	}

	private String string3;

	@Override
	public Object cloneVO()
	{
		throw new RuntimeException("not implemented");
	}

	@Override
	public Object get(String name)
	{

		if ("string3".equals(name))
		{
			return string3;
		}

		throw new RuntimeException("getter for '" + name + "' not implemented");
	}

	@Override
	public de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<?> getAttributeDescriptor(String name)
	{

		for (de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<?> attributeDescriptor : getFieldDescriptors())
		{
			if (attributeDescriptor.getAttributeName().equals(name))
			{
				return attributeDescriptor;
			}
		}

		throw new RuntimeException("unsupported attribute '" + name + "'");
	}

	@Override
	public long getId()
	{
		return id;
	}

	public String getString3()
	{
		return string3;
	}

	@Override
	public void set(String name, Object value)
	{

		if ("string3".equals(name))
		{
			string3 = (String) value;
		}
		else
		{
			throw new RuntimeException("setter for '" + name + "' not implemented");
		}

	}

	@Override
	public void setId(long id)
	{
		this.id = id;
	}

	public void setString3(String string3)
	{
		this.string3 = string3;
	}

}
