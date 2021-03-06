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
package de.pellepelster.myadmin.client.web.modules.dictionary.events;

import com.google.gwt.event.shared.GwtEvent;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public class VOSavedEvent extends GwtEvent<VOEventHandler>
{
	private final IBaseVO baseVO;

	public VOSavedEvent(IBaseVO baseVO)
	{
		super();
		this.baseVO = baseVO;
	}

	public IBaseVO getVo()
	{
		return this.baseVO;
	}

	public static Type<VOEventHandler> TYPE = new Type<VOEventHandler>();

	@Override
	public Type<VOEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(VOEventHandler handler)
	{
		handler.onVOEvent(this.baseVO);
	}

}