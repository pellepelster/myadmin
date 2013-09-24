package de.pellepelster.myadmin.db.index;

public interface ISearchIndexService
{
	static final String DYNAMIC_STRING_FIELD_POSTFIX = "_s";

	static final String SEARCH_INDEX_ELEMENT_TYPE_FIELD_NAME = "type" + DYNAMIC_STRING_FIELD_POSTFIX;

	static final String SEARCH_INDEX_ID_FIELD_NAME = "id";

	void add(ISearchIndexElement element);

	void delete(ISearchIndexElement element);

	void update(ISearchIndexElement element);

	void deleteAll(String elementType);

	long getCount(String elementType);

}
