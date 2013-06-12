package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import com.google.gwt.event.shared.EventHandler;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public interface VOLoadEventHandler extends EventHandler
{
	void onLoad(IBaseVO baseVO);
}