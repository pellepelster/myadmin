package de.pellepelster.myadmin.client.base.modules.dictionary.model.controls;

import java.util.HashMap;
import java.util.Map;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IEnumerationControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public class EnumerationControlModel extends BaseControlModel<IEnumerationControl> implements IEnumerationControlModel
{

	private static final long serialVersionUID = -3831710976796569500L;

	private Map<String, String> enumeration = new HashMap<String, String>();

	public EnumerationControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public Map<String, String> getEnumeration()
	{
		return this.enumeration;
	}

}
