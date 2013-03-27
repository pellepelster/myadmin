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
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBigDecimalControlModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;

/**
 * Float control model
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public class BigDecimalControlModel extends BaseControlModel implements IBigDecimalControlModel
{

	private static final long serialVersionUID = -2342496050752733151L;

	public BigDecimalControlModel()
	{
	}

	public BigDecimalControlModel(IBaseModel parent, DictionaryControlVO dictionaryControlVO)
	{
		super(parent, dictionaryControlVO);
	}

	/** {@inheritDoc} */
	@Override
	public int getFractionDigits()
	{
		if (getDatatypeVO().getFractionDigits() != null)
		{
			return getDatatypeVO().getFractionDigits();
		}
		else
		{
			return FRACTION_DIGITS_DEFAULT;
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getTotalDigits()
	{
		if (getDatatypeVO().getTotalDigits() != null)
		{
			return getDatatypeVO().getTotalDigits();
		}
		else
		{
			return TOTAL_DIGITS_DEFAULT;
		}

	}

	/** {@inheritDoc} */
	@Override
	public Integer getWidthHint()
	{
		if (super.getWidthHint() != null)
		{
			return super.getWidthHint();
		}
		else
		{
			return getTotalDigits() + 1;
		}
	}

}
