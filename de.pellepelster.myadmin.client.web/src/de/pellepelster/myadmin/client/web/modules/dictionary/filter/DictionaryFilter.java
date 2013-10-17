package de.pellepelster.myadmin.client.web.modules.dictionary.filter;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IEditorModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.Composite;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class DictionaryFilter extends BaseModelElement<IEditorModel>
{
	private final Composite rootComposite;

	public DictionaryFilter(IEditorModel editorModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(editorModel);

		this.rootComposite = new Composite(editorModel.getCompositeModel(), voWrapper);
	}

	public Composite getRootComposite()
	{
		return this.rootComposite;
	}

}
