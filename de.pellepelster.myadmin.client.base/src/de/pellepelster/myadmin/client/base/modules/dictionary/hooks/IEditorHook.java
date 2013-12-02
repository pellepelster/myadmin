package de.pellepelster.myadmin.client.base.modules.dictionary.hooks;

import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IButton;
import de.pellepelster.myadmin.client.base.modules.dictionary.editor.IDictionaryEditor;

public interface IEditorHook<VOType extends IBaseVO>
{
	List<IButton> getEditorButtons(IDictionaryEditor<VOType> dictionaryEditor);
}
