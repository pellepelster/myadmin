package de.pellepelster.myadmin.client.base.modules.dictionary.model.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.ITextControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public class BaseTextControlModel extends BaseControlModel<ITextControl> implements ITextControlModel
{

	private static final long serialVersionUID = -6029017257538622486L;

	public BaseTextControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getFormatRegularExpression()
	{
		return null;
	}

	@Override
	public int getMaxLength()
	{
		return MAX_LENGTH_DEFAULT;
	}

	@Override
	public int getMinLength()
	{
		return MIN_LENGTH_DEFAULT;
	}

}
