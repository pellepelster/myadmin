package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IIntegerControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class IntegerControl extends BaseControl<IIntegerControlModel, Integer>
{

	public IntegerControl(IIntegerControlModel integerControlModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(integerControlModel, voWrapper);
	}

}
