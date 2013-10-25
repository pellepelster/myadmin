package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IIntegerControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;

public class IntegerControl extends BaseControl<IIntegerControlModel, Integer>
{

	public IntegerControl(IIntegerControlModel integerControlModel, BaseModelElement<? extends IBaseModel> parent)
	{
		super(integerControlModel, parent);
	}

}
