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
package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import java.util.List;

import de.pellepelster.myadmin.client.base.databinding.IUIObservableValue;
import de.pellepelster.myadmin.client.base.db.vos.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;

/**
 * Control UI
 * 
 * @author pelle
 * 
 */
public interface IControl<WidgetType> extends IUIObservableValue
{

	/** {@inheritDoc} */
	@Override
	Object getContent();

	/**
	 * Returns the control model for the control
	 * 
	 * @return
	 */
	@Override
	IBaseControlModel getModel();

	/**
	 * Returns the widget wrapped by this control
	 * 
	 * @return
	 */
	WidgetType getWidget();

	/** {@inheritDoc} */
	@Override
	void setContent(Object content);

	/** {@inheritDoc} */
	@Override
	void setValidationMessages(List<IValidationMessage> validationMessages);

}
