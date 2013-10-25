package de.pellepelster.myadmin.client.web.test.modules.dictionary;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;

public class TableRowTest<VOType extends IBaseVO>
{

	private ITableRow<VOType> tableRow;

	public TableRowTest(ITableRow<VOType> tableRow)
	{
		this.tableRow = tableRow;
	}

	public <ElementType extends IBaseControl<Value>, Value extends Object> BaseControlElementTest<ElementType, Value> getBaseControlTestElement(
			DictionaryDescriptor<ElementType> controlDescriptor)
	{
		return new BaseControlElementTest<ElementType, Value>(this.tableRow.getElement(controlDescriptor));
	}

}
