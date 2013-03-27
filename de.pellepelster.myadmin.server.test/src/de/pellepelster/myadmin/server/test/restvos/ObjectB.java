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
package de.pellepelster.myadmin.server.test.restvos;

import java.util.HashMap;
import java.util.Map;

public class ObjectB
{

	private String string2;
	private Map<String, ObjectC> map1 = new HashMap<String, ObjectC>();

	public String getString2()
	{
		return string2;
	}

	public void setString2(String string2)
	{
		this.string2 = string2;
	}

	public Map<String, ObjectC> getMap1()
	{
		return map1;
	}

	public void setMap1(Map<String, ObjectC> map1)
	{
		this.map1 = map1;
	}

}
