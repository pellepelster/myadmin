package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class AssignmentTable extends BaseTable<IAssignmentTableModel>
{

	public AssignmentTable(IAssignmentTableModel assignmentTableModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(assignmentTableModel, voWrapper);
	}

}
