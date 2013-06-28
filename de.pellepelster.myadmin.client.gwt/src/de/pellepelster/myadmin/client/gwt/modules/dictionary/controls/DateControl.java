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
package de.pellepelster.myadmin.client.gwt.modules.dictionary.controls;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import de.pellepelster.myadmin.client.base.databinding.IValueChangeListener;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IDateControlModel;
import de.pellepelster.myadmin.client.core.utils.DateTimeFormat;
import de.pellepelster.myadmin.client.gwt.ControlHelper;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IControl;

public class DateControl extends DateBox implements IControl<Widget>
{

	private final IDateControlModel dateControlModel;

	private final ControlHelper gwtControlHelper;

	public DateControl(final IDateControlModel dateControlModel)
	{
		this.dateControlModel = dateControlModel;
		ensureDebugId(DictionaryModelUtil.getDebugId(dateControlModel));

		setFormat(new de.pellepelster.myadmin.client.core.utils.DefaultFormat(DateTimeFormat.getFormat(this.dateControlModel.getFormatPattern())));
		gwtControlHelper = new ControlHelper(this, dateControlModel, false, Date.class);

		addValueChangeHandler(new ValueChangeHandler<Date>()
		{

			@Override
			public void onValueChange(ValueChangeEvent<Date> event)
			{
				gwtControlHelper.fireValueChangeListeners(dateControlModel.getAttributePath(), event.getValue());
			}
		});

	}

	/** {@inheritDoc} */
	@Override
	public void addValueChangeListener(IValueChangeListener valueChangeListener)
	{
		gwtControlHelper.getValueChangeListeners().add(valueChangeListener);
	}

	/** {@inheritDoc} */
	@Override
	public Object getContent()
	{
		return super.getValue();
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getContentType()
	{
		return Date.class;
	}

	/** {@inheritDoc} */
	@Override
	public IBaseControlModel getModel()
	{
		return dateControlModel;
	}

	/** {@inheritDoc} */
	@Override
	public List<IValueChangeListener> getValueChangeListeners()
	{
		return gwtControlHelper.getValueChangeListeners();
	}

	/** {@inheritDoc} */
	@Override
	public Widget getWidget()
	{
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public void removeValueChangeListener(IValueChangeListener valueChangeListener)
	{
		gwtControlHelper.getValueChangeListeners().remove(valueChangeListener);
	}

	/** {@inheritDoc} */
	@Override
	public void setContent(Object content)
	{
		if (content != null)
		{

			if (content instanceof Date)
			{
				super.setValue((Date) content);
			}
			else
			{
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}

		}
		else
		{
			super.setValue(null);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setValidationMessages(List<IValidationMessage> validationMessages)
	{
		gwtControlHelper.setValidationMessages(validationMessages, dateControlModel);
	}

}
