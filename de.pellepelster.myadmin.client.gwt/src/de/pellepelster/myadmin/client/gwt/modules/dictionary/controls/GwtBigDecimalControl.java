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

import com.google.gwt.user.client.ui.TextBox;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.gwt.ControlHelper;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BigDecimalControl;

public class GwtBigDecimalControl extends TextBox
{
	private final BigDecimalControl bigDecimalControl;

	private final ControlHelper gwtControlHelper;

	public GwtBigDecimalControl(BigDecimalControl bigDecimalControl)
	{
		this.bigDecimalControl = bigDecimalControl;
		gwtControlHelper = new ControlHelper(this, bigDecimalControl, true);
		ensureDebugId(DictionaryModelUtil.getDebugId(bigDecimalControl.getModel()));
	}

	public void setContent(Object content)
	{
		if (content != null)
		{
			if (content instanceof BigDecimal)
			{
				super.setValue(bigDecimalControl.format());
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
