package de.pellepelster.myadmin.client.base.modules.dictionary.model.containers;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IEditableTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public abstract class BaseEditableTableModel<VOType extends IBaseVO> extends BaseContainerModel<IEditableTable<VOType>>
{

	private static final long serialVersionUID = 1832725605229414533L;

	public BaseEditableTableModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

}
