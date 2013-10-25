package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IEditableTableModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;

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

	public static BaseContainer<? extends IBaseContainerModel> createContainer(IBaseContainerModel baseContainerModel, BaseModelElement parent)
	{
		if (baseContainerModel instanceof ICompositeModel)
		{
			return new Composite((ICompositeModel) baseContainerModel, parent);
		}
		else if (baseContainerModel instanceof IEditableTableModel)
		{
			return new EditableTable((IEditableTableModel) baseContainerModel, parent);
		}
		else if (baseContainerModel instanceof IAssignmentTableModel)
		{
			return new AssignmentTable((IAssignmentTableModel) baseContainerModel, parent);
		}
		else
		{
			throw new RuntimeException("unsupported cntainer model type '" + baseContainerModel.getClass().getName() + "'");
		}
	}

}
