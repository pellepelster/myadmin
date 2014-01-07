package de.pellepelster.myadmin.client.web.test.modules.dictionary.container;

import java.util.List;

import junit.framework.Assert;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IEditableTable;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

public class EditableTableTest<VOType extends IBaseVO>
{

	private IEditableTable<VOType> editableTable;

	public EditableTableTest(IEditableTable<VOType> editableTable)
	{
		this.editableTable = editableTable;
	}

	public void add(final AsyncCallback<EditableTableTest<VOType>> asyncCallback)
	{
		this.editableTable.add(new BaseErrorAsyncCallback<List<ITableRow<VOType>>>()
		{
			@Override
			public void onSuccess(List<ITableRow<VOType>> result)
			{
				asyncCallback.onSuccess(EditableTableTest.this);
			}
		});
	}

	public void delete(final AsyncCallback<EditableTableTest<VOType>> asyncCallback)
	{
		this.editableTable.delete(new BaseErrorAsyncCallback<List<ITableRow<VOType>>>()
		{
			@Override
			public void onSuccess(List<ITableRow<VOType>> result)
			{
				asyncCallback.onSuccess(EditableTableTest.this);
			}
		});
	}

	public void assertRowCount(int expectedRowCount)
	{
		Assert.assertEquals(expectedRowCount, this.editableTable.getRows().size());
	}

}
