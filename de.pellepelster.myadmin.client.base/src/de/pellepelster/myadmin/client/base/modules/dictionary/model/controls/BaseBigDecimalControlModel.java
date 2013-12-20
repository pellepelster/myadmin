package de.pellepelster.myadmin.client.base.modules.dictionary.model.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBigDecimalControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public abstract class BaseBigDecimalControlModel extends BaseControlModel<IBigDecimalControl> implements IBigDecimalControlModel
{

	private static final long serialVersionUID = 1699242302211790344L;

	public BaseBigDecimalControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public int getFractionDigits()
	{
		return FRACTION_DIGITS_DEFAULT;
	}

	@Override
	public int getTotalDigits()
	{
		return TOTAL_DIGITS_DEFAULT;
	}

}
