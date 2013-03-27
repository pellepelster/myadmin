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
package de.pellepelster.myadmin.client.web.modules.dictionary.databinding;

import de.pellepelster.myadmin.client.base.databinding.IValueChangeListener;

/**
 * Listener for changes to observable values.
 * 
 * @author pelle
 * 
 */
public interface IListValueChangeListener extends IValueChangeListener
{
	/**
	 * Handles a value change in a list
	 * 
	 * @param listValueChangeEvent
	 */
	void handleValueChange(ListValueChangeEvent listValueChangeEvent);
}
