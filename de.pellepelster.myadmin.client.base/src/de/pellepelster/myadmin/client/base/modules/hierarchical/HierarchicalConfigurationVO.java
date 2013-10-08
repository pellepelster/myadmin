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

public class HierarchicalConfigurationVO implements Serializable {

	private static final long serialVersionUID = 4810376846738194939L;

	private Map<String, List<String>> hierarchy = new HashMap<String, List<String>>();

	private String id;

	public HierarchicalConfigurationVO(String id, Map<String, List<String>> hierarchy) {
		this.id = id;
		this.hierarchy = hierarchy;
	}

	public HierarchicalConfigurationVO() {
	}

	public String getId() {
		return this.id;
	}

	public Map<String, List<String>> getHierarchy() {
		return hierarchy;
	}

	public String getTitle() {
		return id;
	}

	public void setTitle(String hierarchicalTree1) {
		// TODO Auto-generated method stub

	}

}
