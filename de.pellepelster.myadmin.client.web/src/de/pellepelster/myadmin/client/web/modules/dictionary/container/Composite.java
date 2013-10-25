package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;

public class Composite extends BaseContainer<ICompositeModel>
{

	public Composite(ICompositeModel composite, BaseModelElement<? extends IBaseModel> parent)
	{
		super(composite, parent);
	}

}
