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

public class AttributeDescriptor<AttributeType> implements IAttributeDescriptor<AttributeType>
{

	private final String attributeName;
	private final Class<?> attributeType;
	private final Class<?> attributeListType;

	public AttributeDescriptor(String attributeName, Class<?> attributeType)
	{
		this(attributeName, attributeType, null);

	}

	public AttributeDescriptor(String attributeName, Class<?> attributeType, Class<?> attributeListType)
	{
		super();

		this.attributeName = attributeName;
		if (attributeType.equals(attributeListType))
		{
			this.attributeType = attributeType;
			this.attributeListType = null;
		}
		else
		{
			this.attributeType = attributeType;
			this.attributeListType = attributeListType;
		}

	}

	/** {@inheritDoc} */
	@Override
	public String getAttributeName()
	{
		return attributeName;
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getAttributeType()
	{
		return attributeType;
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getListAttributeType()
	{
		return attributeListType;
	}

}
