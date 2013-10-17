package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseTableModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class BaseTable<ModelType extends IBaseTableModel> extends BaseContainer<ModelType>
{

	public BaseTable(ModelType baseTable, VOWrapper<IBaseVO> voWrapper)
	{
		super(baseTable, voWrapper);
	}

}
