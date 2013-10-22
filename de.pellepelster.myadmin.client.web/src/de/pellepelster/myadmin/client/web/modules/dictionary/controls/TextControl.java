package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.ITextControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class TextControl extends BaseControl<ITextControlModel, String> implements ITextControl
{

	public TextControl(ITextControlModel textControlModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(textControlModel, voWrapper);
	}

}
