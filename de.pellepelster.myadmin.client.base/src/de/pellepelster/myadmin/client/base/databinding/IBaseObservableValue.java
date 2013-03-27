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
package de.pellepelster.myadmin.client.base.databinding;

import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDatabindingAwareModel;

public interface IBaseObservableValue<ValueChangeListenerType>
{
	/**
	 * Adds a value change listener
	 * 
	 * @param listener
	 */
	void addValueChangeListener(ValueChangeListenerType valueChangeListener);

	/**
	 * Returns the content
	 * 
	 * @return
	 */
	Object getContent();

	/**
	 * The value type of this observable value
	 * 
	 * @return
	 */
	Class<?> getContentType();

	/**
	 * Returns the control model for the control
	 * 
	 * @return
	 */
	IDatabindingAwareModel getModel();

	/**
	 * Returns the registered {@link IValueChangeListener}s
	 * 
	 * @return
	 */
	List<ValueChangeListenerType> getValueChangeListeners();

	/**
	 * Removes a value change listener
	 * 
	 * @param listener
	 */
	void removeValueChangeListener(ValueChangeListenerType valueChangeListener);

	/**
	 * Sets the content
	 * 
	 * @param content
	 */
	void setContent(Object content);
}
