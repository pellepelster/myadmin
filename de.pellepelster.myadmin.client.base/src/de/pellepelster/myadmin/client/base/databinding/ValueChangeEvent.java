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
package de.pellepelster.myadmin.client.base.databinding;

public class ValueChangeEvent
{
	private final String attributePath;
	private final Object attributeValue;

	public ValueChangeEvent(String attributePath, Object attributeValue)
	{
		super();
		this.attributePath = attributePath;
		this.attributeValue = attributeValue;
	}

	public String getAttributePath()
	{
		return attributePath;
	}

	public Object getAttributeValue()
	{
		return attributeValue;
	}

}
