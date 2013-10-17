package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IEditableTableModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class ContainerFactory
{
	private static ContainerFactory instance;

	public static ContainerFactory getInstance()
	{
		if (instance == null)
		{
			instance = new ContainerFactory();
		}

		return instance;
	}

	public static BaseContainer createContainer(IBaseContainerModel baseContainerModel, VOWrapper<IBaseVO> voWrapper)
	{
		if (baseContainerModel instanceof ICompositeModel)
		{
			return new Composite((ICompositeModel) baseContainerModel, voWrapper);
		}
		else if (baseContainerModel instanceof IEditableTableModel)
		{
			return new EditableTable((IEditableTableModel) baseContainerModel, voWrapper);
		}
		else if (baseContainerModel instanceof IAssignmentTableModel)
		{
			return new AssignmentTable((IAssignmentTableModel) baseContainerModel, voWrapper);
		}
		else
		{
			throw new RuntimeException("unsupported cntainer model type '" + baseContainerModel.getClass().getName() + "'");
		}
	}

}
