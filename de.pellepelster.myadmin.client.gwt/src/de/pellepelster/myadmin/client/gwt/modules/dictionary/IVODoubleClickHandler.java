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
package de.pellepelster.myadmin.client.gwt.modules.dictionary;

/**
 * Handler for double click events on VO's (usually in lists or tables9
 * 
 * @author pelle
 * 
 */
public interface IVODoubleClickHandler<VOType>
{

	/**
	 * Called on double click event
	 * 
	 * @param vo
	 */
	void doubleClick(VOType vo);
}
