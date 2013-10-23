package de.pellepelster.myadmin.client.base.modules.dictionary.container;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public interface IBaseTable<VOType extends IBaseVO> extends IBaseContainer
{
	public interface TableUpdateListener
	{
		void onUpdate();
	}

	interface ITableRow<RowVOType extends IBaseVO>
	{
		RowVOType getVO();
	}

	void addTableUpdateListeners(TableUpdateListener tableUpdateListener);
}
