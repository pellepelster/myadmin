package de.pellepelster.myadmin.client.gwt.modules.dictionary.controls;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.Column;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;

public class TableRowColumn extends Column<IBaseTable.ITableRow<IBaseVO>, String>
{
	private IBaseControlModel baseControlModel;

	public TableRowColumn(IBaseControlModel baseControlModel, Cell<String> cell)
	{
		super(cell);
		this.baseControlModel = baseControlModel;
	}

	@Override
	public String getValue(IBaseTable.ITableRow<IBaseVO> tableRow)
	{
		return tableRow.getElement(baseControlModel).format();
	}
};