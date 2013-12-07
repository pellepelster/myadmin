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

import java.io.Serializable;
import java.util.HashMap;

/**
 * Basic VO interface
 * 
 * @author pelle
 * @version $Rev: 662 $, $Date: 2010-08-30 20:58:04 +0200 (Mon, 30 Aug 2010) $
 * 
 */
public interface IBaseVO extends Serializable
{

	public static long NEW_VO_ID = 0;

	public static final de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor<Long> FIELD_ID = new de.pellepelster.myadmin.client.base.db.vos.AttributeDescriptor<Long>(
			"id", Long.class, Long.class);

	/**
	 * Creates a deep copy of this VO
	 * 
	 * @return
	 */
	Object cloneVO();

	/**
	 * Generic getter to overcome the lack of reflection on the client
	 * 
	 * @param name
	 * @return
	 */
	Object get(String name);

	/**
	 * Returns the {@link IAttributeDescriptor} for a given attribute
	 * 
	 * @param name
	 * @return
	 */
	IAttributeDescriptor<?> getAttributeDescriptor(String name);

	/**
	 * Returns the id for the VO
	 * 
	 * @return
	 */
	long getId();

	/**
	 * Returns the oid for the VO
	 * 
	 * @return
	 */
	long getOid();

	boolean isNew();

	/**
	 * Generic setter to overcome the lack of reflection on the client
	 * 
	 * @param name
	 * @return
	 */
	public void set(String name, Object value);

	/**
	 * Sets the id for the VO
	 * 
	 * @return
	 */
	void setId(long id);

	/**
	 * Sets the id for the VO
	 * 
	 * @return
	 */
	void setOid(long oid);

	/**
	 * Generic data map
	 * 
	 * @return
	 */
	HashMap<String, Object> getData();

}
