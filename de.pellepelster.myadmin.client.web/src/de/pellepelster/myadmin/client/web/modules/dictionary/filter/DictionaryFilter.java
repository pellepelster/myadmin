package de.pellepelster.myadmin.client.web.modules.dictionary.filter;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IEditorModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.Composite;

public class DictionaryFilter extends BaseDictionaryElement<IEditorModel>
{
	private final Composite rootComposite;

	public DictionaryFilter(IEditorModel editorModel, BaseDictionaryElement<IBaseModel> parent)
	{
		super(editorModel, parent);

		this.rootComposite = new Composite(editorModel.getCompositeModel(), parent);
	}

	public Composite getRootComposite()
	{
		return this.rootComposite;
	}

}
