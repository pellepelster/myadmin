package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import java.util.Enumeration;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IEnumerationControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class EnumerationControl extends BaseControl<IEnumerationControlModel, Enumeration>
{

	public EnumerationControl(IEnumerationControlModel enumerationControlModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(enumerationControlModel, voWrapper);
	}

}
