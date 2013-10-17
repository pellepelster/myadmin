package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class ReferenceControl extends BaseControl<IReferenceControlModel>
{

	public ReferenceControl(IReferenceControlModel referenceControlModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(referenceControlModel, voWrapper);
	}

	@Override
	public String format()
	{
		if (getValue() != null && getValue() instanceof IBaseVO)
		{
			return DictionaryUtil.getLabel(getModel(), (IBaseVO) getValue());
		}
		else
		{
			return super.format();
		}
	}
}
