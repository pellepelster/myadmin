package de.pellepelster.myadmin.client.gwt.modules.dictionary.controls;

import com.google.gwt.user.client.ui.Label;

import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;

public class ReadonlyControl extends Label
{

	private BaseControl baseControl;

	public ReadonlyControl(BaseControl baseControl)
	{
		super();
		this.baseControl = baseControl;
	}

	public void setContent(Object content)
	{
		setText(baseControl.format());
	}

}
