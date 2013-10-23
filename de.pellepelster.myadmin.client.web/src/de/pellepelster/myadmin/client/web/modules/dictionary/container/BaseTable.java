package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseTableModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;

public class BaseTable<ModelType extends IBaseTableModel> extends BaseContainer<ModelType>
{

	public BaseTable(ModelType baseTable, BaseModelElement<IBaseModel> parent)
	{
		super(baseTable, parent);
	}

}
