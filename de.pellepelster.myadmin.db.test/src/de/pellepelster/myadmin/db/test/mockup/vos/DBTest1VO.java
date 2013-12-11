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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.pellepelster.myadmin.client.base.db.vos.AttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.BaseVO;
import de.pellepelster.myadmin.client.base.db.vos.ChangeTrackingArrayList;
import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;

public class DBTest1VO extends BaseVO
{

	public enum TEST_ENUM_VO
	{
		ENUM1, ENUM2
	}

	private static final long serialVersionUID = 6869411076738783234L;

	public static final IAttributeDescriptor<List<DBTest2VO>> TEST2S = new AttributeDescriptor<List<DBTest2VO>>("test2s", List.class, DBTest2VO.class);

	public static final IAttributeDescriptor<Integer> TESTINTEGER = new AttributeDescriptor<Integer>("testInteger", Integer.class);

	public static final IAttributeDescriptor<String> TESTSTRING = new AttributeDescriptor<String>("testString", String.class);

	public static final IAttributeDescriptor<TEST_ENUM_VO> TESTENUM = new AttributeDescriptor<TEST_ENUM_VO>("testEnum", TEST_ENUM_VO.class);

	public static final IAttributeDescriptor<String> MAP = new AttributeDescriptor<String>("map", Map.class);

	public static IAttributeDescriptor<?>[] getFieldDescriptors()
	{
		return new IAttributeDescriptor[] { TEST2S, TESTINTEGER, TESTSTRING, TESTENUM, MAP };
	}

	private Map<String, String> map = new HashMap<String, String>();

	private long id;

	private List<DBTest2VO> test2s = new ChangeTrackingArrayList<DBTest2VO>();

	private TEST_ENUM_VO testEnum;;

	private int testInteger;

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

	public List<DBTest2VO> getTest2s()
	{
		return this.test2s;
	}

	public TEST_ENUM_VO getTestEnum()
	{
		return this.testEnum;
	}

	public int getTestInteger()
	{
		return this.testInteger;
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

	public Map<String, String> getMap()
	{
		return this.map;
	}

	public void setMap(Map<String, String> map)
	{
		getChangeTracker().addChange("map", map);

		this.map = map;
	}

	public void setTestEnum(TEST_ENUM_VO testEnum)
	{
		getChangeTracker().addChange("testEnum", testEnum);

		this.testEnum = testEnum;
	}

	public void setTestInteger(int testInteger)
	{
		getChangeTracker().addChange("testInteger", testInteger);

		this.testInteger = testInteger;
	}

	public void setTestString(String testString)
	{
		getChangeTracker().addChange("testString", testString);

		this.testString = testString;
	}

}
