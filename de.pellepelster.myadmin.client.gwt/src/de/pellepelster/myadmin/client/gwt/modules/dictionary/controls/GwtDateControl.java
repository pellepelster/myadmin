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

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.core.utils.DateTimeFormat;
import de.pellepelster.myadmin.client.gwt.ControlHelper;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.DateControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IGwtControl;

public class GwtDateControl extends DateBox implements IGwtControl
{

	private final DateControl dateControl;

	public GwtDateControl(final DateControl dateControl)
	{
		this.dateControl = dateControl;
		ensureDebugId(DictionaryModelUtil.getDebugId(dateControl.getModel()));

		String format = this.dateControl.getModel().getFormatPattern();

		if (format == null)
		{
			format = DateTimeFormat.getFormat(com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat.DATE_MEDIUM).getPattern();
		}

		setFormat(new de.pellepelster.myadmin.client.core.utils.DefaultFormat(DateTimeFormat.getFormat(format)));
		new ControlHelper(this, dateControl, this, false);
	}

	/** {@inheritDoc} */
	@Override
	public Widget getWidget()
	{
		return this;
	}

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

}
