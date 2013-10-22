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

import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBigDecimalControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BigDecimalControl;

/**
 * control factory for float controls
 * 
 * @author pelle
 * 
 */
public class BigDecimalControlFactory extends BaseControlFactory<IBigDecimalControlModel, BigDecimalControl>
{

	/** {@inheritDoc} */
	@Override
	public Widget createControl(BigDecimalControl bigDecimalControl, LAYOUT_TYPE layoutType)
	{
		return new GwtBigDecimalControl(bigDecimalControl);
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl<?, ?> baseControlModel)
	{
		return baseControlModel instanceof BigDecimalControl;
	}

}
