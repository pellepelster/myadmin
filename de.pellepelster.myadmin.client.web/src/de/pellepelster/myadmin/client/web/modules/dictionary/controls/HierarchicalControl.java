package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;

public class HierarchicalControl extends BaseControl<IHierarchicalControlModel, IBaseVO>
{

	public HierarchicalControl(IHierarchicalControlModel hierarchicalControlModel, BaseModelElement<? extends IBaseModel> parent)
	{
		super(hierarchicalControlModel, parent);
	}

}
