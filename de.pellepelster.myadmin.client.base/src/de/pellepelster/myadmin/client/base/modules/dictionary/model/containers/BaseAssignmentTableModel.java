package de.pellepelster.myadmin.client.base.modules.dictionary.model.containers;

import de.pellepelster.myadmin.client.base.modules.dictionary.container.IComposite;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public abstract class BaseAssignmentTableModel extends BaseContainerModel<IComposite>
{

	private static final long serialVersionUID = 1832725605229414533L;

	public BaseAssignmentTableModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

}
