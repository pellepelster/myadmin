package de.pellepelster.myadmin.db.index;

import java.util.Map;

public interface ISearchIndexElementQuery
{
	Map<String, String> getIdFields();

	String getType();

}
