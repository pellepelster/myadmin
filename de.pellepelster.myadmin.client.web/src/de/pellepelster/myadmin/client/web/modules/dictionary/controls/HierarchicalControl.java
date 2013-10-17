package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class HierarchicalControl extends BaseControl<IHierarchicalControlModel>
{

	public HierarchicalControl(IHierarchicalControlModel hierarchicalControlModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(hierarchicalControlModel, voWrapper);
	}

}
