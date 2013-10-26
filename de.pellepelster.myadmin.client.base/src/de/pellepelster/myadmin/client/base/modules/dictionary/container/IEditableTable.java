package de.pellepelster.myadmin.client.base.modules.dictionary.container;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public interface IEditableTable<VOType extends IBaseVO> extends IBaseTable<VOType>
{

	void delete(ITableRow<VOType> tableRow);

	void add(AsyncCallback<List<ITableRow<VOType>>> asyncCallback);

}
