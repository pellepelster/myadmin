package de.pellepelster.myadmin.client.base.modules.dictionary.model.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IIntegerControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public class BaseIntegerControlModel extends BaseControlModel<IIntegerControl> implements IIntegerControlModel
{

	private static final long serialVersionUID = 3758940598822644209L;

	public BaseIntegerControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public Integer getMax()
	{
		return MAX_DEFAULT;
	}

	@Override
	public Integer getMin()
	{
		return MIN_DEFAULT;
	}

}
