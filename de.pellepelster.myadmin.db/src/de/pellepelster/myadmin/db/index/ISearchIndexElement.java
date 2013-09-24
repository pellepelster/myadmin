package de.pellepelster.myadmin.db.index;

import java.util.Map;

public interface ISearchIndexElement
{
	Map<String, String> getIdFields();

	String getType();

	String getId();

}
