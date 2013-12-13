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
public interface IHierarchicalVO extends IBaseHierarchical, IBaseVO
{
	public static final IAttributeDescriptor<IHierarchicalVO> FIELD_PARENT = new AttributeDescriptor<IHierarchicalVO>("parent", IHierarchicalVO.class,
			IHierarchicalVO.class);

	public static final IAttributeDescriptor<String> FIELD_PARENT_CLASSNAME = new AttributeDescriptor<String>("parentClassName", String.class, String.class);

	public static final IAttributeDescriptor<Long> FIELD_PARENT_ID = new AttributeDescriptor<Long>("parentId", Long.class, Long.class);

	/**
	 * Returns whether this node has children
	 * 
	 * @return
	 */
	boolean getHasChildren();

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
	void setParent(IHierarchicalVO parent);

	/**
	 * Returns the parent specified by
	 * {@link IBaseHierarchical#getParentClassName()} and
	 * {@link IBaseHierarchical#getParentId()}
	 * 
	 * @return
	 */
	IHierarchicalVO getParent();

}
