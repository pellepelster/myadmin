package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IEditableTableModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class EditableTable extends BaseTable<IEditableTableModel>
{

	public EditableTable(IEditableTableModel editableTableModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(editableTableModel, voWrapper);
	}

}
