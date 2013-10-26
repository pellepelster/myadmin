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

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.TextControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.IValidator;

public class TextControlFactory extends BaseControlFactory<ITextControlModel, TextControl>
{

	private static final List<IValidator> VALIDATORS = Arrays.asList(new IValidator[] {});

	/** {@inheritDoc} */
	@Override
	public Widget createControl(TextControl textControl, LAYOUT_TYPE layoutType)
	{
		if (textControl.getModel().isReadonly())
		{
			return new ReadonlyControl(textControl);
		}
		else
		{
			return new GwtTextControl(textControl);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl<?, ?> baseControl)
	{
		return baseControl instanceof TextControl;
	}

	/** {@inheritDoc} */
	@Override
	public List<IValidator> createValidators(TextControl textControl)
	{
		return addValidators(textControl, VALIDATORS);
	}

}
