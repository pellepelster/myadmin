package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;

public class AssignmentTable<VOType extends IBaseVO> extends BaseTable<VOType, IAssignmentTableModel>
{

	public AssignmentTable(IAssignmentTableModel assignmentTableModel, BaseModelElement<IBaseModel> parent)
	{
		super(assignmentTableModel, parent);
	}

}
