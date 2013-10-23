package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;

public class TableRow<VOType extends IBaseVO> implements IBaseTable.ITableRow<VOType>
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