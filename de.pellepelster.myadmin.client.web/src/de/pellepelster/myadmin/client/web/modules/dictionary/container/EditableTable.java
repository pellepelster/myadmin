package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IEditableTableModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;

public class EditableTable extends BaseTable<IEditableTableModel>
{

	public EditableTable(IEditableTableModel editableTableModel, BaseModelElement<IBaseModel> parent)
	{
		super(editableTableModel, parent);
	}

}
