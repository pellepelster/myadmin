package de.pellepelster.myadmin.db.index;

import java.util.HashMap;
import java.util.Map;

public class SearchIndexElementQuery implements ISearchIndexElementQuery
{
	private final String type;

	private String text;

	private Map<String, String> fields = new HashMap<String, String>();

	public SearchIndexElementQuery(String type)
	{
		this.type = type;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	@Override
	public String getType()
	{
		return this.type;
	}

	@Override
	public String getText()
	{
		return this.text;
	}

	@Override
	public Map<String, String> getFields()
	{
		return this.fields;
	}

}
