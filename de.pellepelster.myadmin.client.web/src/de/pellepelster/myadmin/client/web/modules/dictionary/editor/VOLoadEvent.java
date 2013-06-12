package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import com.google.gwt.event.shared.GwtEvent;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public class VOLoadEvent extends GwtEvent<VOLoadEventHandler>
{
	private IBaseVO baseVO;

	public VOLoadEvent(IBaseVO baseVO)
	{
		this.baseVO = baseVO;
	}

	public static Type<VOLoadEventHandler> TYPE = new Type<VOLoadEventHandler>();

	@Override
	public Type<VOLoadEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(VOLoadEventHandler handler)
	{
		handler.onLoad(this.baseVO);
	}

}