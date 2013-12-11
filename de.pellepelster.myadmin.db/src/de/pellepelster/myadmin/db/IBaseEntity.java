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
package de.pellepelster.myadmin.db;

/**
 * Describes a basic entity
 * 
 * @author pelle
 * @version $Rev: 662 $, $Date: 2010-08-30 20:58:04 +0200 (Mon, 30 Aug 2010) $
 * 
 */
public interface IBaseEntity
{

	public static String FIELD_ID = "id";

	/**
	 * Returns the unique id for this entity
	 * 
	 * @return
	 */
	long getId();

	long getOid();

	boolean isNew();

	void setOid(long oid);
}
