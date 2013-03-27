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
package de.pellepelster.myadmin.client.web.test;

import de.pellepelster.myadmin.client.base.db.vos.BaseVO;
import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;

public abstract class BaseHierarchicalTestVO extends BaseVO implements IHierarchicalVO
{
	enum ENUM1
	{
		ENUM1_VALUE1, ENUM1_VALUE2, ENUM1_VALUE3
	};

	private static final long serialVersionUID = 5114820135502917898L;

	private long id;

	public static final de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<String> FIELD_STRING1 = new de.pellepelster.myadmin.client.base.db.vos.AttributeDescriptor<String>(
			"string1", String.class, String.class);

	private String string1;

	private String parentClassName;

	private Long parentId;

	private boolean hasChildren;

	private IHierarchicalVO parent;

	public BaseHierarchicalTestVO(IHierarchicalVO parent)
	{
		super();
		this.parent = parent;
	}

	public static de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<?>[] getFieldDescriptors()
	{
		return new de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor[] {

		FIELD_ID, FIELD_STRING1

		};
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
	public Object cloneVO()
	{
		throw new RuntimeException("not implemented");
	}

	@Override
	public Object get(String name)
	{

		if ("string1".equals(name))
		{
			return string1;
		}

		if ("parent".equals(name))
		{
			return parent;
		}

		throw new RuntimeException("getter for '" + name + "' not implemented");
	}

	@Override
	public long getId()
	{
		return id;
	}

	public String getString1()
	{
		return string1;
	}

	@Override
	public void set(String name, Object value)
	{

		if ("string1".equals(name))
		{
			string1 = (String) value;
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

	public void setString1(String string1)
	{
		this.string1 = string1;
	}

	@Override
	public IHierarchicalVO getParent()
	{
		return parent;
	}

	@Override
	public boolean hasChildren()
	{
		return hasChildren;
	}

	@Override
	public void setHasChildren(boolean hasChildren)
	{
		this.hasChildren = hasChildren;
	}

	@Override
	public void setParent(IHierarchicalVO parent)
	{
		this.parent = parent;
	}

	@Override
	public void setParentClassName(String parentClassName)
	{
		this.parentClassName = parentClassName;
	}

	@Override
	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	@Override
	public String getParentClassName()
	{
		return parentClassName;
	}

	@Override
	public Long getParentId()
	{
		return parentId;
	}

}
