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
package de.pellepelster.myadmin.db;

import javax.persistence.Transient;

public abstract class BaseHierarchicalEntity implements IHierarchicalEntity
{
	@javax.persistence.Column(name = "parent_id")
	private Long parentId;

	@javax.persistence.Column(name = "parent")
	private String parentClassName;

	@Transient
	private boolean hasChildren;

	@Transient
	private IHierarchicalEntity parent;

	/** {@inheritDoc} */
	@Override
	public IHierarchicalEntity getParent()
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
	public void setParent(IHierarchicalEntity parent)
	{
		this.parent = parent;
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
