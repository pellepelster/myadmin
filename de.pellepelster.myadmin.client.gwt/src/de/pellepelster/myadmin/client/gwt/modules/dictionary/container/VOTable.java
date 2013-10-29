package de.pellepelster.myadmin.client.gwt.modules.dictionary.container;

import java.util.List;

import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.gwt.ControlHandler;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseDataGrid;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;

public class VOTable<VOType extends IBaseVO> extends BaseDataGrid<VOType>
{

	private final ListDataProvider<IBaseTable.ITableRow<VOType>> dataProvider = new ListDataProvider<IBaseTable.ITableRow<VOType>>();

	public VOTable(List<BaseDictionaryControl<?, ?>> baseControls)
	{
		super(baseControls);

		createModelColumns();
		dataProvider.addDataDisplay(this);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Column<IBaseTable.ITableRow<VOType>, ?> getColumn(BaseDictionaryControl baseControl)
	{
		return (Column<IBaseTable.ITableRow<VOType>, ?>) ControlHandler.getInstance().createColumn(baseControl, false, dataProvider, this);
	}

	public void setContent(List<IBaseTable.ITableRow<VOType>> content)
	{
		dataProvider.setList(content);
	}

}
