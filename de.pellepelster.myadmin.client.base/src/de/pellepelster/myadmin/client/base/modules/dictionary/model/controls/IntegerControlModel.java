package de.pellepelster.myadmin.client.base.modules.dictionary.model.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IIntegerControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public class IntegerControlModel extends BaseControlModel<IIntegerControl> implements IIntegerControlModel
{

	private static final long serialVersionUID = 3758940598822644209L;

	private Integer max = MAX_DEFAULT;

	private Integer min = MIN_DEFAULT;

	public IntegerControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public Integer getMax()
	{
		return this.max;
	}

	@Override
	public Integer getMin()
	{
		return this.min;
	}

	public void setMax(Integer max)
	{
		this.max = max;
	}

	public void setMin(Integer min)
	{
		this.min = min;
	}

}
