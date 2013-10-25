package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBooleanControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;

public class BooleanControl extends BaseControl<IBooleanControlModel, BooleanControl>
{

	public BooleanControl(IBooleanControlModel booleanControlModel, BaseModelElement<? extends IBaseModel> parent)
	{
		super(booleanControlModel, parent);
	}

}
