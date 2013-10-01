package de.pellepelster.myadmin.db.index;

import java.util.HashMap;
import java.util.Map;

public class SearchIndexElementQuery implements ISearchIndexElementQuery
{
	private Map<String, String> idFields = new HashMap<String, String>();

	private final String type;

	public SearchIndexElementQuery(String type, Map<String, String> idFields)
	{
		this.type = type;
		this.idFields.putAll(idFields);
	}

	@Override
	public String getType()
	{
		return this.type;
	}

	@Override
	public Map<String, String> getIdFields()
	{
		return this.idFields;
	}

}
