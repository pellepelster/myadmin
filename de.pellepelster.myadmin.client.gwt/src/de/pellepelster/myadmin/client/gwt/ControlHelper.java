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
package de.pellepelster.myadmin.client.gwt;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.ValidationUtils;
import de.pellepelster.myadmin.client.web.modules.dictionary.layout.WidthCalculationStrategy;

public class ControlHelper
{
	private final UIObject uiObject;

	private List<IValidationMessage> validationMessages = new ArrayList<IValidationMessage>();

	public <ValueType> ControlHelper(final Widget widget, final BaseDictionaryControl<?, ValueType> baseControl, boolean addValueChangeListener)
	{
		this.uiObject = widget;

		widget.setWidth(WidthCalculationStrategy.getInstance().getControlWidthCss(baseControl.getModel()));

		if (widget instanceof HasValue<?>)
		{
			final HasValue<ValueType> hasValueWidget = (HasValue<ValueType>) widget;

			if (addValueChangeListener)
			{
				widget.addDomHandler(new BlurHandler()
				{
					@Override
					public void onBlur(BlurEvent event)
					{
						if (hasValueWidget.getValue() == null || hasValueWidget.getValue().toString().isEmpty())
						{
							baseControl.setValue(hasValueWidget.getValue());
						}
					}
				}, BlurEvent.getType());

				widget.addDomHandler(new ChangeHandler()
				{
					/** {@inheritDoc} */
					@Override
					public void onChange(ChangeEvent event)
					{
						baseControl.setValue(hasValueWidget.getValue());
					}
				}, ChangeEvent.getType());
			}
		}
	}

	public void removeAllValidationMessages()
	{
		validationMessages.clear();
	}

	public void setValidationMessages(List<IValidationMessage> validationMessages, IBaseControlModel baseControlModel)
	{
		this.validationMessages = validationMessages;

		if (validationMessages.isEmpty())
		{
			uiObject.removeStyleName(GwtStyles.CONTROL_ERROR_STYLE);
			uiObject.setTitle("");
		}
		else
		{
			uiObject.addStyleName(GwtStyles.CONTROL_ERROR_STYLE);
			uiObject.setTitle(ValidationUtils.getValidationMessageString(validationMessages, baseControlModel));
		}
	}
}
