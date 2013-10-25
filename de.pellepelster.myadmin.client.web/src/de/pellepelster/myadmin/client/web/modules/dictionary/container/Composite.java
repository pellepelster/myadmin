package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class Composite extends BaseContainerElement<ICompositeModel>
{

	public Composite(ICompositeModel composite, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(composite, parent);
	}

}
