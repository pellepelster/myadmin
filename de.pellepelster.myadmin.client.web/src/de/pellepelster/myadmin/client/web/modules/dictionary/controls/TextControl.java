package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.ITextControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;

public class TextControl extends BaseControl<ITextControlModel, String> implements ITextControl
{

	public TextControl(ITextControlModel textControlModel, BaseModelElement<IBaseModel> parent)
	{
		super(textControlModel, parent);
	}

}
