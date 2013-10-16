package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBooleanControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class BooleanControl extends BaseControl<IBooleanControlModel>
{

	public BooleanControl(IBooleanControlModel booleanControlModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(booleanControlModel, voWrapper);
	}

}
