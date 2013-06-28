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
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IDateControlModel;
import de.pellepelster.myadmin.client.base.util.ObjectUtils;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;

/**
 * Date control model
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public class DateControlModel extends BaseControlModel implements IDateControlModel
{
	private static final long serialVersionUID = 6685144462878662940L;

	private String formatPattern;

	public DateControlModel()
	{
	}

	public DateControlModel(IBaseModel parent, DictionaryControlVO dictionaryControlVO, String formatPattern)
	{
		super(parent, dictionaryControlVO);
		this.formatPattern = formatPattern;
	}

	/** {@inheritDoc} */
	@Override
	public Integer getWidthHint()
	{
		return ObjectUtils.firstNonNull(getWidthHintInternal(), getFormatPattern().length());
	}

	@Override
	public String getFormatPattern()
	{
		return this.formatPattern;
	}

}
