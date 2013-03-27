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

public class VOSavedEvent extends GwtEvent<VOSavedEventHandler>
{
	private final IBaseVO vo;

	public VOSavedEvent(IBaseVO vo)
	{
		super();
		this.vo = vo;
	}

	public IBaseVO getVo()
	{
		return vo;
	}

	public static Type<VOSavedEventHandler> TYPE = new Type<VOSavedEventHandler>();

	@Override
	public Type<VOSavedEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(VOSavedEventHandler handler)
	{
		handler.onVOSaved(this);
	}

}