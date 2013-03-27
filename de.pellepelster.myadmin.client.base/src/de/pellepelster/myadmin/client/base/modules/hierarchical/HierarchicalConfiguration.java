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
package de.pellepelster.myadmin.client.base.modules.hierarchical;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HierarchicalConfiguration implements Serializable
{
	private static final long serialVersionUID = 4968319675803615636L;

	private Map<String, List<String>> hierarchy = new HashMap<String, List<String>>();

	private String id;

	private String title;

	public HierarchicalConfiguration()
	{
		super();
	}

	public HierarchicalConfiguration(String id, Map<String, List<String>> hierarchy)
	{
		super();
		this.id = id;
		this.hierarchy = hierarchy;
	}

	/**
	 * Returns the hierarchy configuration (Dictionary Name -> List of possible
	 * parents)
	 * 
	 * @return
	 */
	public Map<String, List<String>> getHierarchy()
	{
		return hierarchy;
	}

	public String getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

}
