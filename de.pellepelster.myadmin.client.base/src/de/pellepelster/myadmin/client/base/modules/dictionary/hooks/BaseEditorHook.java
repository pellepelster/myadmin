package de.pellepelster.myadmin.client.base.modules.dictionary.hooks;

import java.util.Collections;
import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IButton;
import de.pellepelster.myadmin.client.base.modules.dictionary.editor.IDictionaryEditor;

public abstract class BaseEditorHook<VOType extends IBaseVO> implements IEditorHook<VOType>
{

	@Override
	public List<IButton> getEditorButtons(IDictionaryEditor<VOType> dictionaryEditor)
	{
		return Collections.emptyList();
	}

}
