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
package de.pellepelster.myadmin.client.base.db.vos;

public abstract class BaseHierarchicalVO implements IHierarchicalVO
{
	private static final long serialVersionUID = -8460027159195011886L;

	private Long parentId;

	private String parentClassName;

	private boolean hasChildren;

	private IHierarchicalVO parent;

	/** {@inheritDoc} */
	@Override
	public IHierarchicalVO getParent()
	{
		return parent;
	}

	/** {@inheritDoc} */
	@Override
	public String getParentClassName()
	{
		return parentClassName;
	}

	/** {@inheritDoc} */
	@Override
	public Long getParentId()
	{
		return parentId;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasChildren()
	{
		return hasChildren;
	}

	/** {@inheritDoc} */
	@Override
	public void setHasChildren(boolean hasChildren)
	{
		this.hasChildren = hasChildren;
	}

	/** {@inheritDoc} */
	@Override
	public void setParent(IHierarchicalVO parent)
	{
		this.parent = parent;
		this.parentId = parent.getId();
		this.parentClassName = parent.getClass().getName();
	}

	/** {@inheritDoc} */
	@Override
	public void setParentClassName(String parentClassName)
	{
		this.parentClassName = parentClassName;
	}

	/** {@inheritDoc} */
	@Override
	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}
}
