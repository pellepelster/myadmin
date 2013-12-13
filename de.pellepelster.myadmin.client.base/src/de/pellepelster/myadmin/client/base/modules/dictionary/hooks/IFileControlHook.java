package de.pellepelster.myadmin.client.base.modules.dictionary.hooks;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IFileControl;

public interface IFileControlHook
{
	void onLinkActivate(IFileControl fileControl);
}
