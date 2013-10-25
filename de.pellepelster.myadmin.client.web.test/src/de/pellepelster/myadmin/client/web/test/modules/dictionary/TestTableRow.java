package de.pellepelster.myadmin.client.web.test.modules.dictionary;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;

public class TestTableRow<VOType extends IBaseVO> {

	private ITableRow<VOType> tableRow;
	
	public TestTableRow(ITableRow<VOType> tableRow) {
		this.tableRow = tableRow;
	}
	
	public <ElementType extends IBaseControl> TestBaseDictionaryControl getElement(DictionaryDescriptor<ElementType> controlDescriptor)
	{
		return new TestBaseDictionaryControl(tableRow.getElement(controlDescriptor));
	}


}
