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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import de.pellepelster.myadmin.db.BaseEntity;
import de.pellepelster.myadmin.db.IBaseEntity;

@Entity
@Table(name = "test2")
public class DBTest2 extends BaseEntity implements IBaseEntity
{

	public static final String TEST1 = "test1";

	public static final String TEST3 = "test3";

	public static final String TESTSTRING = "testString";

	@Id
	@Column(name = "test2_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "test2_id_seq")
	@SequenceGenerator(name = "test2_id_seq", sequenceName = "test2_id_seq", allocationSize = 1)
	private int id;

	@OneToOne
	private DBTest1 test1;

	@OneToOne
	private DBTest3 test3;

	@Column(name = "test2_testString")
	private String testString;

	@Override
	public long getId()
	{
		return this.id;
	}

	public DBTest1 getTest1()
	{
		return this.test1;
	}

	public DBTest3 getTest3()
	{
		return this.test3;
	}

	public String getTestString()
	{
		return this.testString;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setTest1(DBTest1 test1)
	{
		this.test1 = test1;
	}

	public void setTest3(DBTest3 test3)
	{
		this.test3 = test3;
	}

	public void setTestString(String testString)
	{
		this.testString = testString;
	}

}
