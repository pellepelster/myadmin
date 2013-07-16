package de.pellepelster.myadmin.dsl.query;

import de.pellepelster.myadmin.dsl.myAdminDsl.Dictionary;

public class DictionaryQuery
{
	private Dictionary dictionary;

	public DictionaryQuery(Dictionary dictionary)
	{
		this.dictionary = dictionary;
	}

	public Dictionary getDictionary()
	{
		return this.dictionary;
	}

	public DictionaryEditorQuery getDictionaryEditor()
	{
		return new DictionaryEditorQuery(this.dictionary.getDictionaryeditor());
	}

}
