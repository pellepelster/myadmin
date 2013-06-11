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
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.IValidator;

/**
 * control factory for text controls
 * 
 * @author pelle
 * 
 */
public class TextControlFactory extends BaseControlFactory<ITextControlModel>
{

	private static final List<IValidator> VALIDATORS = Arrays.asList(new IValidator[] {});

	/** {@inheritDoc} */
	@Override
	public IControl<Widget> createControl(ITextControlModel controlModel, LAYOUT_TYPE layoutType)
	{
		if (controlModel.isReadonly())
		{
			return new ReadonlyControl(controlModel, this);
		}
		else
		{
			return new TextControl(controlModel);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(IBaseControlModel baseControlModel)
	{
		return baseControlModel instanceof ITextControlModel;
	}

	/** {@inheritDoc} */
	@Override
	public List<IValidator> createValidators(ITextControlModel controlModel)
	{
		return addValidators(controlModel, VALIDATORS);
	}

}
