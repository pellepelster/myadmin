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

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.rpc.GwtTransient;

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
	@GwtTransient
	private DateTimeFormat dateTimeFormat;

	private static final long serialVersionUID = 4527174051951928193L;

	public DateControlModel()
	{
		this.dateTimeFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM);
	}

	public DateControlModel(IBaseModel parent, DictionaryControlVO dictionaryControlVO)
	{
		super(parent, dictionaryControlVO);
	}

	/** {@inheritDoc} */
	@Override
	public Integer getWidthHint()
	{
		return ObjectUtils.firstNonNull(getWidthHintInternal(), getFormat().getPattern().length());
	}

	@Override
	public DateTimeFormat getFormat()
	{
		return this.dateTimeFormat;
	}

}
