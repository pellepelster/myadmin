package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IEnumerationControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class EnumerationControl extends BaseDictionaryControl<IEnumerationControlModel, Object>
{

	public EnumerationControl(IEnumerationControlModel enumerationControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(enumerationControlModel, parent);
	}

	@Override
	public String format()
	{
		if (getValue() == null)
		{
			return "";
		}
		else
		{
			return getModel().getEnumeration().get(getValue()).toString();
		}
	}

}
