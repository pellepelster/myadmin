package de.pellepelster.myadmin.client.base.modules.dictionary.model.controls;

import java.util.Map;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IEnumerationControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public abstract class BaseEnumerationControlModel extends BaseControlModel<IEnumerationControl> implements IEnumerationControlModel
{

	private static final long serialVersionUID = -3831710976796569500L;

	public BaseEnumerationControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public Map<String, String> getEnumeration()
	{
		throw new RuntimeException("no enumeration map set");
	}

}
