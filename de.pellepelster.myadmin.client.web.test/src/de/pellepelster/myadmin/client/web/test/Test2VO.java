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
package de.pellepelster.myadmin.client.web.test;

import java.math.BigDecimal;
import java.util.Date;

import de.pellepelster.myadmin.client.base.db.vos.BaseVO;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public class Test2VO extends BaseVO implements IBaseVO
{

	enum ENUM2
	{
		ENUM2_VALUE1, ENUM2_VALUE2, ENUM2_VALUE3
	}

	private static final long serialVersionUID = -665701982873718230L;;

	private long id;

	public static final de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<String> FIELD_STRING2 = new de.pellepelster.myadmin.client.base.db.vos.AttributeDescriptor<String>(
			"string2", String.class, String.class);

	private String string2;

	public static final de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<Integer> FIELD_INTEGER2 = new de.pellepelster.myadmin.client.base.db.vos.AttributeDescriptor<Integer>(
			"integer2", Integer.class, Integer.class);

	private Integer integer2;

	public static final de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<BigDecimal> FIELD_BIGDECIMAL2 = new de.pellepelster.myadmin.client.base.db.vos.AttributeDescriptor<BigDecimal>(
			"bigDecimal2", BigDecimal.class, BigDecimal.class);

	private BigDecimal bigDecimal2;

	public static final de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<Boolean> FIELD_BOOLEAN2 = new de.pellepelster.myadmin.client.base.db.vos.AttributeDescriptor<Boolean>(
			"boolean2", Boolean.class, Boolean.class);

	private Boolean boolean2;

	public static final de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<Date> FIELD_DATE2 = new de.pellepelster.myadmin.client.base.db.vos.AttributeDescriptor<Date>(
			"date2", Date.class, Date.class);

	private Date date2;

	public static final de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<ENUM2> FIELD_ENUMERATION2 = new de.pellepelster.myadmin.client.base.db.vos.AttributeDescriptor<ENUM2>(
			"enumeration2", ENUM2.class, ENUM2.class);

	private ENUM2 enumeration2;

	public static final de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<Test3VO> FIELD_TEST3VO = new de.pellepelster.myadmin.client.base.db.vos.AttributeDescriptor<Test3VO>(
			"test3VO", Test3VO.class, Test3VO.class);

	public static de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<?>[] getFieldDescriptors()
	{
		return new de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor[] {

		FIELD_BIGDECIMAL2, FIELD_BOOLEAN2, FIELD_DATE2, FIELD_ENUMERATION2, FIELD_ID, FIELD_INTEGER2, FIELD_STRING2, FIELD_TEST3VO

		};
	}

	private Test3VO test3VO;

	@Override
	public Object cloneVO()
	{
		Test2VO clone = new Test2VO();

		clone.setBigDecimal2(bigDecimal2);
		clone.setBoolean2(boolean2);
		clone.setDate2(date2);
		clone.setEnumeration2(enumeration2);
		clone.setId(id);
		clone.setInteger2(integer2);
		clone.setString2(string2);
		clone.setTest3VO(test3VO);

		return clone;
	}

	@Override
	public Object get(String name)
	{

		if ("string2".equals(name))
		{
			return string2;
		}

		if ("integer2".equals(name))
		{
			return integer2;
		}

		if ("bigDecimal2".equals(name))
		{
			return bigDecimal2;
		}

		if ("boolean2".equals(name))
		{
			return boolean2;
		}

		if ("date2".equals(name))
		{
			return date2;
		}

		if ("enumeration2".equals(name))
		{
			return enumeration2;
		}

		if ("oid".equals(name))
		{
			return getOid();
		}

		if ("test3VO".equals(name))
		{
			return test3VO;
		}

		throw new RuntimeException("getter for '" + name + "' not implemented");
	}

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

	public BigDecimal getBigDecimal2()
	{
		return bigDecimal2;
	}

	public Boolean getBoolean2()
	{
		return boolean2;
	}

	public Date getDate2()
	{
		return date2;
	}

	public ENUM2 getEnumeration2()
	{
		return enumeration2;
	}

	@Override
	public long getId()
	{
		return id;
	}

	public Integer getInteger2()
	{
		return integer2;
	}

	public String getString2()
	{
		return string2;
	}

	public Test3VO getTest3VO()
	{
		return test3VO;
	}

	@Override
	public void set(String name, Object value)
	{

		if ("string2".equals(name))
		{
			string2 = (String) value;
		}
		else if ("integer2".equals(name))
		{
			integer2 = (Integer) value;
		}
		else if ("bigDecimal2".equals(name))
		{
			bigDecimal2 = (BigDecimal) value;
		}
		else if ("boolean2".equals(name))
		{
			boolean2 = (Boolean) value;
		}
		else if ("date2".equals(name))
		{
			date2 = (Date) value;
		}
		else if ("enumeration2".equals(name))
		{
			if (value instanceof String)
			{
				enumeration2 = ENUM2.valueOf((String) value);
			}
			else
			{
				enumeration2 = (ENUM2) value;
			}

		}
		else if ("test3VO".equals(name))
		{
			test3VO = (Test3VO) value;
		}
		else
		{
			throw new RuntimeException("setter for '" + name + "' not implemented");
		}

	}

	public void setBigDecimal2(BigDecimal bigDecimal2)
	{
		this.bigDecimal2 = bigDecimal2;
	}

	public void setBoolean2(Boolean boolean2)
	{
		this.boolean2 = boolean2;
	}

	public void setDate2(Date date2)
	{
		this.date2 = date2;
	}

	public void setEnumeration2(ENUM2 enumeration2)
	{
		this.enumeration2 = enumeration2;
	}

	@Override
	public void setId(long id)
	{
		this.id = id;
	}

	public void setInteger2(Integer integer2)
	{
		this.integer2 = integer2;
	}

	public void setString2(String string2)
	{
		this.string2 = string2;
	}

	public void setTest3VO(Test3VO test3VO)
	{
		this.test3VO = test3VO;
	}

}
