package de.pellepelster.myadmin.client.base.modules.dictionary.model.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IFileControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public class BaseFileControlModel extends BaseControlModel<IFileControl> implements IFileControlModel
{

	private static final long serialVersionUID = -4845737977264107889L;

	public BaseFileControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

}
