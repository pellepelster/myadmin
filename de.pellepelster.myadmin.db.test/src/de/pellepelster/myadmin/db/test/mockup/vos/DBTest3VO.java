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
package de.pellepelster.myadmin.db.test.mockup.vos;

import de.pellepelster.myadmin.client.base.db.vos.AttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.BaseVO;
import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;

public class DBTest3VO extends BaseVO
{

	private static final long serialVersionUID = -1511549650470779575L;

	public static final IAttributeDescriptor<String> TESTSTRING = new AttributeDescriptor<String>("testString", String.class);

	public static final IAttributeDescriptor<DBTest1VO> TEST1 = new AttributeDescriptor<DBTest1VO>("test1", DBTest1VO.class);

	public static de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor[] getFieldDescriptors()
	{
		return new de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor[] {

		TESTSTRING, TEST1

		};
	}

	private long id;

	private DBTest1VO test1;

	private String testString;

	/** {@inheritDoc} */
	@Override
	public Object cloneVO()
	{
		throw new RuntimeException("not implemented");
	}

	/** {@inheritDoc} */
	@Override
	public Object get(String name)
	{
		throw new RuntimeException("not implemented");
	}

	/** {@inheritDoc} */
	@Override
	public de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<?> getAttributeDescriptor(String name)
	{

		for (de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<?> attributeDescriptor : getFieldDescriptors())
		{
			if (attributeDescriptor.getAttributeName().equals(name))
			{
				return attributeDescriptor;
			}
		}

		throw new RuntimeException("unsupported attribute '" + name + "'");
	}

	/** {@inheritDoc} */
	@Override
	public long getId()
	{
		return this.id;
	}

	public DBTest1VO getTest1()
	{
		return this.test1;
	}

	public String getTestString()
	{
		return this.testString;
	}

	/** {@inheritDoc} */
	@Override
	public void set(String name, Object value)
	{
		throw new RuntimeException("not implemented");
	}

	/** {@inheritDoc} */
	@Override
	public void setId(long id)
	{
		getChangeTracker().addChange("id", id);

		this.id = id;
	}

	public void setTest1(DBTest1VO test1)
	{
		getChangeTracker().addChange("test1", test1);

		this.test1 = test1;
	}

	public void setTestString(String testString)
	{
		getChangeTracker().addChange("testString", testString);

		this.testString = testString;
	}

}
