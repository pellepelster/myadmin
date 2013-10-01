package de.pellepelster.myadmin.db.index;

public interface ISearchIndexService
{

	void add(ISearchIndexElement element);

	void delete(ISearchIndexElement element);

	void update(ISearchIndexElement element);

	void deleteAll(String elementType);

	long getCount(String elementType);

}