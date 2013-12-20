package de.pellepelster.myadmin.client.base.modules.dictionary.model.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IHierarchicalControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public class BaseHierarchicalControlModel extends BaseControlModel<IHierarchicalControl> implements IHierarchicalControlModel
{

	private static final long serialVersionUID = -947831635255212542L;

	public BaseHierarchicalControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getHierarchicalId()
	{
		throw new RuntimeException("no hierarchical id set");
	}

}
