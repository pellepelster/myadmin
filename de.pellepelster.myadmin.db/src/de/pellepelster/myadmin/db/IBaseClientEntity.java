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
 * Interface for a client aware entity
 * 
 * @author pelle
 * @version $Rev: 801 $, $Date: 2008-10-25 19:40:49 +0200 (Sat, 25 Oct 2008) $
 * 
 */
public interface IBaseClientEntity extends IBaseEntity
{
	/**
	 * The client associated with this entity
	 * 
	 * @return
	 */
	public IClient getClient();

	/**
	 * Associates a client with this entity
	 * 
	 * @param client
	 */
	public void setClient(IClient client);
}