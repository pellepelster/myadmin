package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IIntegerControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class IntegerControl extends BaseDictionaryControl<IIntegerControlModel, Integer>
{

	public IntegerControl(IIntegerControlModel integerControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(integerControlModel, parent);
	}

}
