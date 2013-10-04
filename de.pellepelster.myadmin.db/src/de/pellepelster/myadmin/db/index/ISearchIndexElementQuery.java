package de.pellepelster.myadmin.db.index;

import java.util.Map;

public interface ISearchIndexElementQuery
{
	String getType();

	String getText();

	Map<String, String> getFields();
}
