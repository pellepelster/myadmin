package de.pellepelster.myadmin.client.base.modules.dictionary.model.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBooleanControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public abstract class BaseBooleanControlModel extends BaseControlModel<IBooleanControl> implements IBooleanControlModel
{

	private static final long serialVersionUID = 532719232119633708L;

	public BaseBooleanControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

}
