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

import java.util.Map;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IEnumarationControlModel;
import de.pellepelster.myadmin.client.base.util.ObjectUtils;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;

/**
 * Enumeration control model
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public class EnumerationControlModel extends BaseControlModel implements IEnumarationControlModel
{
	private static final long serialVersionUID = -2124985849853567977L;

	public EnumerationControlModel()
	{
	}

	public EnumerationControlModel(IBaseModel parent, DictionaryControlVO dictionaryControlVO)
	{
		super(parent, dictionaryControlVO);
	}

	/** {@inheritDoc} */
	@Override
	public Map<String, String> getEnumeration()
	{
		return getDatatypeVO().getEnumerationValues();
	}

	/** {@inheritDoc} */
	@Override
	public Integer getWidthHint()
	{
		Integer maxLength = null;

		for (String value : getEnumeration().values())
		{
			if (maxLength == null || value.length() > maxLength)
			{
				maxLength = value.length();
			}
		}

		return ObjectUtils.firstNonNull(getWidthHintInternal(), maxLength, DEFAULT_WIDTH_HINT);
	}

}
