package de.pellepelster.myadmin.client.base.modules.dictionary.hooks;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public interface IEditorSaveHook<VOType extends IBaseVO>
{

	void onSave(AsyncCallback<Boolean> asyncCallback, VOType vo);

}
