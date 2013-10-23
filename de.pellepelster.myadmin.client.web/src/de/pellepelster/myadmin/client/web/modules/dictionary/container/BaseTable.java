package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseTableModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;

public class BaseTable<VOType extends IBaseVO, ModelType extends IBaseTableModel> extends BaseContainer<ModelType> implements IBaseTable<VOType>
{
	public class TableRow implements ITableRow<VOType>
	{
		private final VOType vo;

		public TableRow(VOType vo)
		{
			super();
			this.vo = vo;
		}

		@Override
		public VOType getVO()
		{
			return this.vo;
		}

	}

	private List<TableRow> rows = new ArrayList<TableRow>();

	private List<TableUpdateListener> tableUpdateListeners = new ArrayList<TableUpdateListener>();

	public BaseTable(ModelType baseTable, BaseModelElement<IBaseModel> parent)
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

	protected List<TableRow> getRows()
	{
		return this.rows;
	}

	protected ITableRow<VOType> addRow(VOType vo)
	{
		TableRow tableRow = new TableRow(vo);
		this.rows.add(tableRow);

		return tableRow;
	}

}
