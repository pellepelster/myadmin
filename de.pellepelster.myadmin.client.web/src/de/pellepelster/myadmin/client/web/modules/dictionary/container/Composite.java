package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class Composite extends BaseContainer<ICompositeModel>
{

	public Composite(ICompositeModel composite, VOWrapper<IBaseVO> voWrapper)
	{
		super(composite, voWrapper);
	}

}
