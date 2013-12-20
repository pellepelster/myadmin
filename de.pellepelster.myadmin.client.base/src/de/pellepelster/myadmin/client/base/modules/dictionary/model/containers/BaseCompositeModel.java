package de.pellepelster.myadmin.client.base.modules.dictionary.model.containers;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IAssignmentTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public abstract class BaseCompositeModel<VOType extends IBaseVO> extends BaseContainerModel<IAssignmentTable<VOType>>
{

	private static final long serialVersionUID = 1832725605229414533L;

	public BaseCompositeModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

}
