package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IEditorModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.Composite;

public class DictionaryEditor extends BaseDictionary<IEditorModel>
{

	private final Composite rootComposite;

	public DictionaryEditor(IEditorModel editorModel)
	{
		super(editorModel);

		this.rootComposite = new Composite(editorModel.getCompositeModel());
	}

	public Composite getRootComposite()
	{
		return this.rootComposite;
	}

}
