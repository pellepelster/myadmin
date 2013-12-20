package de.pellepelster.myadmin.client.web.test.modules.dictionary.container;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.BaseControlTest;

public class TableRowTest<VOType extends IBaseVO>
{

	private ITableRow<VOType> tableRow;

	public TableRowTest(ITableRow<VOType> tableRow)
	{
		this.tableRow = tableRow;
	}

	public <ElementType extends IBaseControl<Value, ?>, Value extends Object> BaseControlTest<ElementType, Value> getBaseControlTestElement(
			BaseModel<ElementType> baseModel)
	{
		return new BaseControlTest<ElementType, Value>(this.tableRow.getElement(baseModel));
	}

}
