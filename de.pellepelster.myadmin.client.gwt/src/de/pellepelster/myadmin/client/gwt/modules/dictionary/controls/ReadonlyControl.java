package de.pellepelster.myadmin.client.gwt.modules.dictionary.controls;

import com.google.gwt.user.client.ui.Label;

import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;

public class ReadonlyControl extends Label
{

	private BaseDictionaryControl baseControl;

	public ReadonlyControl(BaseDictionaryControl baseControl)
	{
		super();
		this.baseControl = baseControl;
	}

	public void setContent(Object content)
	{
		setText(baseControl.format());
	}

}
