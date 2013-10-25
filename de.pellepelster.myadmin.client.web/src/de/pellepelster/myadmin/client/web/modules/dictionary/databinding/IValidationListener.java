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

import de.pellepelster.myadmin.client.base.databinding.ValueChangeEvent;

/**
 * Interface for validation event notification
 * 
 * @author pelle
 * 
 */
@Deprecated
public interface IValidationListener
{

	/**
	 * Gets called after validation
	 * 
	 * @param valueChangeEvent
	 *            the {@link ValueChangeEvent} that triggered the validation
	 */
	void afterValidate(ValueChangeEvent valueChangeEvent);

}
