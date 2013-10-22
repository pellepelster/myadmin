package de.pellepelster.myadmin.client.web.test.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IReferenceControl;

public class ReferenceControlTest<VOType extends IBaseVO> extends BaseControlTest<IReferenceControl<VOType>, VOType>
{

	public ReferenceControlTest(IReferenceControl<VOType> referenceControl)
	{
		super(referenceControl);
	}

}
