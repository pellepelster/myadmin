package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import java.util.Enumeration;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IEnumerationControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;

public class EnumerationControl extends BaseControl<IEnumerationControlModel, Enumeration>
{

	public EnumerationControl(IEnumerationControlModel enumerationControlModel, BaseModelElement<IBaseModel> parent)
	{
		super(enumerationControlModel, parent);
	}

}
