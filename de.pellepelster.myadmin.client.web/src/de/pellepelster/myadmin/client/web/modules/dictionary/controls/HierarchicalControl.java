package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IHierarchicalControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class HierarchicalControl extends BaseDictionaryControl<IHierarchicalControlModel, IHierarchicalVO> implements
		IHierarchicalControl
{

	public HierarchicalControl(IHierarchicalControlModel hierarchicalControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(hierarchicalControlModel, parent);
	}

	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		throw new RuntimeException("not implemented");
	}

}
