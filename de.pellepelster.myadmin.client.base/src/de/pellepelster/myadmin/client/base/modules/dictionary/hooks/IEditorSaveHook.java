package de.pellepelster.myadmin.client.base.modules.dictionary.hooks;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IEditorSaveHook
{
	void onSave(AsyncCallback<Void> asyncCallback);
}
