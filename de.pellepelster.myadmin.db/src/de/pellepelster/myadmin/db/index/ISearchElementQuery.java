package de.pellepelster.myadmin.db.index;

import java.util.Map;

public interface ISearchElementQuery
{
	String getType();

	String getId();

	Map<String, Object> getFields();

	String getLabel();

}
