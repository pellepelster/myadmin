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
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IIntegerControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IntegerControl;

/**
 * control factory for integer controls
 * 
 * @author pelle
 * 
 */
public class IntegerControlFactory extends BaseControlFactory<IIntegerControlModel, IntegerControl>
{

	/** {@inheritDoc} */
	@Override
	public Widget createControl(IntegerControl integerControl, LAYOUT_TYPE layoutType)
	{
		return new GwtIntegerControl(integerControl);
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl<?, ?> baseControl)
	{
		return baseControl instanceof IntegerControl;
	}

}
