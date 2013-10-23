package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;

public class ReferenceControl extends BaseControl<IReferenceControlModel, IBaseVO>
{

	public ReferenceControl(IReferenceControlModel referenceControlModel, BaseModelElement<IBaseModel> parent)
	{
		super(referenceControlModel, parent);
	}

	@Override
	public String format()
	{
		if (getValue() != null && getValue() instanceof IBaseVO)
		{
			return DictionaryUtil.getLabel(getModel(), getValue());
		}
		else
		{
			return super.format();
		}
	}
}
