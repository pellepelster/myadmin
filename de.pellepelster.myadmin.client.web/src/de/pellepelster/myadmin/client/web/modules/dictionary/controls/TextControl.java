package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.ITextControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class TextControl extends BaseDictionaryControl<ITextControlModel, String> implements ITextControl
{

	public TextControl(ITextControlModel textControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(textControlModel, parent);
	}


}
