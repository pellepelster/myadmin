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
package de.pellepelster.myadmin.db.test.mockup.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import de.pellepelster.myadmin.db.BaseEntity;
import de.pellepelster.myadmin.db.IBaseEntity;

@Entity
@Table(name = "test1")
public class DBTest1 extends BaseEntity implements IBaseEntity
{

	public enum TEST_ENUM
	{
		ENUM1, ENUM2
	}

	@Id
	@Column(name = "test1_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "test1_id_seq")
	@SequenceGenerator(name = "test1_id_seq", sequenceName = "test1_id_seq", allocationSize = 1)
	private long id;

	@Column(name = "test1_testinteger")
	private int testInteger;

	@Column(name = "test1_teststring")
	private String testString;

	@javax.persistence.OneToMany()
	@Column(name = "test1_test2s")
	private List<DBTest2> test2s = new ArrayList<DBTest2>();

	@Column(name = "test1_testenum")
	private TEST_ENUM testEnum;

	@javax.persistence.ElementCollection
	private Map<String, String> map = new HashMap<String, String>();

	public Map<String, String> getMap()
	{
		return this.map;
	}

	public void setMap(Map<String, String> map)
	{
		this.map = map;
	}

	@Override
	public long getId()
	{
		return this.id;
	}

	public List<DBTest2> getTest2s()
	{
		return this.test2s;
	}

	public TEST_ENUM getTestEnum()
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

	public void setId(long id)
	{
		this.id = id;
	}

	public void setTest2s(List<DBTest2> test2s)
	{
		this.test2s = test2s;
	}

	public void setTestEnum(TEST_ENUM testEnum)
	{
		this.testEnum = testEnum;
	}

	public void setTestInteger(int testInteger)
	{
		this.testInteger = testInteger;
	}

	public void setTestString(String testString)
	{
		this.testString = testString;
	}

}
