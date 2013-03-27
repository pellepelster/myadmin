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
package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

/**
 * Dirtylistener interface
 * 
 * @author pelle
 * 
 */
public interface IDirtyListener
{

	/**
	 * Dirty status changed to clean
	 */
	void markClean();

	/**
	 * Dirty status changed to dirty
	 */
	void markDirty();

}
