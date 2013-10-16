package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IEditorModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class DictionaryEditor extends BaseRootElement<IEditorModel>
{

	public DictionaryEditor(IEditorModel editorModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(editorModel, voWrapper);

	}
}
