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

import java.util.ArrayList;
import java.util.List;

public class ObjectA
{

	private String string1;
	private List<ObjectB> list1 = new ArrayList<ObjectB>();

	public String getString1()
	{
		return string1;
	}

	public void setString1(String string1)
	{
		this.string1 = string1;
	}

	public List<ObjectB> getList1()
	{
		return list1;
	}

	public void setList1(List<ObjectB> list1)
	{
		this.list1 = list1;
	}

}
