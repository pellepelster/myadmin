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
package de.pellepelster.myadmin.db.jpql;

/**
 * Generic interface for alias aware elements (entities or joins) in a JQPL
 * query
 * 
 * @author pelle
 * @version $Rev: 1 $, $Date: 2009-04-15 01:03:38 +0400 (Wed, 15 Apr 2009) $
 * 
 */
public interface IAliasProvider
{

	/**
	 * Returns the alias
	 * 
	 * @return
	 */
	String getNewAlias();

}
