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

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.SuggestCellControl.SuggestCellSuggestion;

public class VOSuggestion implements SuggestCellSuggestion<IBaseVO>
{

	private final String displayString;
	private final IBaseVO vo;

	public VOSuggestion(String displayString, IBaseVO vo)
	{
		super();
		this.displayString = displayString;
		this.vo = vo;
	}

	@Override
	public String getDisplayString()
	{
		return displayString;
	}

	@Override
	public String getReplacementString()
	{
		return displayString;
	}

	public IBaseVO getVo()
	{
		return vo;
	}

	@Override
	public IBaseVO getValue()
	{
		return vo;
	}

}