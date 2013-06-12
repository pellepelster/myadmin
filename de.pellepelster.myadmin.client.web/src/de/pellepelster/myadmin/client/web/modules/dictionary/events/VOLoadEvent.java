package de.pellepelster.myadmin.client.web.modules.dictionary.events;

import com.google.gwt.event.shared.GwtEvent;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public class VOLoadEvent extends GwtEvent<VOEventHandler>
{
	private IBaseVO baseVO;

	public VOLoadEvent(IBaseVO baseVO)
	{
		this.baseVO = baseVO;
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