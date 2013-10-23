package de.pellepelster.myadmin.client.base.modules.dictionary.container;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public interface IEditableTable<VOType extends IBaseVO> extends IBaseTable<VOType>
{

	void add(AsyncCallback<ITableRow<VOType>> asyncCallback);

}
