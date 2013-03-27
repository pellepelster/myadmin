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

import java.util.Date;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

/**
 * General info attributes for an entity
 * 
 * @author pelle
 * @version $Rev: 1030 $, $Date: 2011-04-27 17:34:07 +0200 (Wed, 27 Apr 2011) $
 * 
 */
public interface IBaseInfoEntity extends IBaseVO
{
	/**
	 * The date of entity creation
	 * 
	 * @return
	 */
	Date getCreateDate();

	/**
	 * Create username
	 * 
	 * @return
	 */
	String getCreateUser();

	/**
	 * Last update date
	 * 
	 * @return
	 */
	Date getUpdateDate();

	/**
	 * Last update username
	 * 
	 * @return
	 */
	String getUpdateUser();

	/**
	 * Sets create date
	 * 
	 * @param createDate
	 */
	void setCreateDate(Date createDate);

	/**
	 * Sets create username
	 * 
	 * @param createUser
	 */
	void setCreateUser(String createUser);

	/**
	 * Sets update date
	 * 
	 * @param updateDate
	 */
	void setUpdateDate(Date updateDate);

	/**
	 * Sets update username
	 * 
	 * @param updateUser
	 */
	void setUpdateUser(String updateUser);
}