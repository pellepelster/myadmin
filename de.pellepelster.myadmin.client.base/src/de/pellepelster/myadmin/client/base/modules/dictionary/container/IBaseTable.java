package de.pellepelster.myadmin.client.base.modules.dictionary.container;

import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;

public interface IBaseTable<VOType extends IBaseVO> extends IBaseContainer
{
	public interface TableUpdateListener
	{
		void onUpdate();
	}

	interface ITableRow<RowVOType extends IBaseVO>
	{

		RowVOType getVO();

		@SuppressWarnings("rawtypes")
		<ElementType extends IBaseControl> ElementType getElement(BaseModel<ElementType> baseModel);

		@SuppressWarnings("rawtypes")
		<ElementType extends IBaseControl> ElementType getElement(IBaseControlModel baseControlModel);

	}

	void addTableUpdateListeners(TableUpdateListener tableUpdateListener);

	List<ITableRow<VOType>> getRows();

	List<ITableRow<VOType>> getSelection();

}
