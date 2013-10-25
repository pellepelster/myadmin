package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseTableModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class BaseTable<VOType extends IBaseVO, ModelType extends IBaseTableModel> extends BaseContainerElement<ModelType> implements IBaseTable<VOType>
{

	private List<ITableRow<VOType>> rows = new ArrayList<ITableRow<VOType>>();

	private List<TableUpdateListener> tableUpdateListeners = new ArrayList<TableUpdateListener>();

	public BaseTable(ModelType baseTable, BaseDictionaryElement<IBaseModel> parent)
	{
		super(baseTable, parent);
	}

	protected List<TableUpdateListener> getTableUpdateListeners()
	{
		return this.tableUpdateListeners;
	}

	@Override
	public void addTableUpdateListeners(TableUpdateListener tableUpdateListener)
	{
		this.tableUpdateListeners.add(tableUpdateListener);
	}

	@Override
	public List<ITableRow<VOType>> getRows()
	{
		return this.rows;
	}

	public void setRows(List<VOType> vos)
	{
		this.rows.clear();
		this.rows.addAll(vos2TableRows(vos));
	}

	protected ITableRow<VOType> addRow(VOType vo)
	{
		TableRow<VOType, ModelType> tableRow = new TableRow<VOType, ModelType>(vo, this);
		this.rows.add(tableRow);

		return tableRow;
	}
	
	private List<IBaseTable.ITableRow<VOType>> vos2TableRows(List<VOType> vos)
	{
		List<IBaseTable.ITableRow<VOType>> result = new ArrayList<IBaseTable.ITableRow<VOType>>();

		for (VOType vo : vos)
		{
			result.add(new TableRow<VOType, IBaseTableModel>(vo, (BaseTable<VOType, IBaseTableModel>) this));
		}

		return result;
	}
	
	public ITableRow<VOType> getTableRow(int rowIndex)
	{
		return rows.get(rowIndex);
	}

}
