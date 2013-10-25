package de.pellepelster.myadmin.client.base.modules.dictionary.container;

import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;

public interface IBaseTable<VOType extends IBaseVO> extends IBaseContainer
{
	public interface TableUpdateListener
	{
		void onUpdate();
	}

	interface ITableRow<RowVOType extends IBaseVO>
	{
		RowVOType getVO();
		
		<ElementType extends IBaseControl<?>> ElementType getElement(DictionaryDescriptor<ElementType> controlDescriptor);

	}

	void addTableUpdateListeners(TableUpdateListener tableUpdateListener);

	List<ITableRow<VOType>> getRows();
	
}
