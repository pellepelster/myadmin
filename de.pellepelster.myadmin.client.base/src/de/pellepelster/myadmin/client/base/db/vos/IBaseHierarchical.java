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

/**
 * Interface for nodes in trees
 * 
 * @author pelle
 * 
 */
public interface IBaseHierarchical<HierarchicalType>
{
	/**
	 * Returns the parent specified by
	 * {@link IBaseHierarchical#getParentClassName()} and
	 * {@link IBaseHierarchical#getParentId()}
	 * 
	 * @return
	 */
	HierarchicalType getParent();

	/**
	 * Returns the parents classname
	 * 
	 * @return
	 */
	String getParentClassName();

	/**
	 * Returns the parents id
	 * 
	 * @return
	 */
	Long getParentId();

	/**
	 * Returns whether this node has children
	 * 
	 * @return
	 */
	boolean hasChildren();

	/**
	 * Sets the children for this node
	 * 
	 * @param hasChildren
	 */
	void setHasChildren(boolean hasChildren);

	/**
	 * Sets the parent
	 * 
	 * @param parent
	 */
	void setParent(HierarchicalType parent);

	/**
	 * Sets the parent classname
	 * 
	 * @param parentClassName
	 */
	void setParentClassName(String parentClassName);

	/**
	 * Sets the parent id
	 * 
	 * @param parentId
	 */
	void setParentId(Long parentId);

}
