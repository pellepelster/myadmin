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
package de.pellepelster.myadmin.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import de.pellepelster.myadmin.db.BaseEntity;
import de.pellepelster.myadmin.db.IBaseEntity;

@Entity
@Table(name = "user_groups")
public class Group extends BaseEntity implements IBaseEntity, GrantedAuthority
{

	/**  */
	private static final long serialVersionUID = 4076673785311896982L;

	@Column(name = "group_description")
	private String description;

	@Id
	@Column(name = "group_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_groups_group_id_seq")
	@SequenceGenerator(name = "user_groups_group_id_seq", sequenceName = "user_groups_group_id_seq", allocationSize = 1)
	private long id;

	@Column(name = "group_name")
	private String name;

	/** {@inheritDoc} */
	@Override
	public String getAuthority()
	{
		return this.name;
	}

	public String getDescription()
	{
		return this.description;
	}

	/** {@inheritDoc} */
	@Override
	public long getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
