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

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.databinding.IValueChangeListener;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBigDecimalControlModel;
import de.pellepelster.myadmin.client.gwt.ControlHelper;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IUIControl;

public class BigDecimalControl extends TextBox implements IUIControl<Widget>
{
	private final IBigDecimalControlModel bigDecimalControlModel;

	private final ControlHelper gwtControlHelper;

	public BigDecimalControl(IBigDecimalControlModel bigDecimalControlModel)
	{
		this.bigDecimalControlModel = bigDecimalControlModel;
		gwtControlHelper = new ControlHelper(this, bigDecimalControlModel, true, BigDecimal.class);
		ensureDebugId(DictionaryModelUtil.getDebugId(bigDecimalControlModel));
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
		return BigDecimal.class;
	}

	/** {@inheritDoc} */
	@Override
	public IBaseControlModel getModel()
	{
		return bigDecimalControlModel;
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

			if (content instanceof BigDecimal)
			{
				super.setValue(format(bigDecimalControlModel, content));
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

	public static String format(IBigDecimalControlModel controlModel, Object value)
	{

		if (value != null)
		{
			if (value instanceof BigDecimal)
			{
				return NumberFormat.getDecimalFormat().format((BigDecimal) value);
			}
			else
			{
				return value.toString();
			}
		}
		else
		{
			return "";
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setValidationMessages(List<IValidationMessage> validationMessages)
	{
		gwtControlHelper.setValidationMessages(validationMessages, bigDecimalControlModel);
	}

}
