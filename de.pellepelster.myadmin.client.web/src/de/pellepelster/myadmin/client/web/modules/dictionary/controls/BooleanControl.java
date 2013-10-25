package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBooleanControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class BooleanControl extends BaseDictionaryControl<IBooleanControlModel, BooleanControl>
{

	public BooleanControl(IBooleanControlModel booleanControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(booleanControlModel, parent);
	}

}
