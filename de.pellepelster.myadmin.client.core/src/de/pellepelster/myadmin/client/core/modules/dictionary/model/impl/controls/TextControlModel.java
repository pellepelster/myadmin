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
package de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;

/**
 * Text control model
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public class TextControlModel extends BaseControlModel implements ITextControlModel
{
	private static final long serialVersionUID = 3104478942640611699L;

	public TextControlModel()
	{
	}

	public TextControlModel(IBaseModel parent, DictionaryControlVO dictionaryControlVO)
	{
		super(parent, dictionaryControlVO);
	}

	/** {@inheritDoc} */
	@Override
	public String getFormatRegularExpression()
	{
		return getDatatypeVO().getFormatRegularExpression();
	}

	/** {@inheritDoc} */
	@Override
	public int getMaxLength()
	{
		return getDatatypeVO().getMaxLength();
	}

	/** {@inheritDoc} */
	@Override
	public Integer getMinLength()
	{
		return getDatatypeVO().getMinLength();
	}

	@Override
	public Integer getWidthHint()
	{
		if (super.getWidthHint() != null)
		{
			return super.getWidthHint();
		}
		else
		{
			return getMinLength();
		}
	}

}
