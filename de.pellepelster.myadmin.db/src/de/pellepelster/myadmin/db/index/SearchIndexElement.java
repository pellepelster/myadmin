package de.pellepelster.myadmin.db.index;

import java.util.Map;

public class SearchIndexElement extends SearchIndexElementQuery implements ISearchIndexElement
{
	private final String text;

	public SearchIndexElement(String type, Map<String, String> idFields, String text)
	{
		super(type, idFields);
		this.text = text;
	}

	@Override
	public String getText()
	{
		return this.text;
	}

}
