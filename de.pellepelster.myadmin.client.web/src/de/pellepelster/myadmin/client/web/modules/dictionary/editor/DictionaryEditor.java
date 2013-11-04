package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IEditorModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class DictionaryEditor extends BaseRootElement<IEditorModel>
{
	private VOWrapper<IBaseVO> voWrapper;

	public DictionaryEditor(IEditorModel editorModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(editorModel, null);
		this.voWrapper = voWrapper;

	}

	@Override
	protected VOWrapper<IBaseVO> getVOWrapper()
	{
		return this.voWrapper;
	}

}
