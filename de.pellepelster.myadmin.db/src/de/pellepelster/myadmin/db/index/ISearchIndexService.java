package de.pellepelster.myadmin.db.index;

import java.util.List;

public interface ISearchIndexService
{

	void add(ISearchIndexElement element);

	void delete(ISearchIndexElement element);

	void update(ISearchIndexElement element);

	void deleteAll(ISearchIndexElementQuery elementQuery);

	long getCount(ISearchIndexElementQuery elementQuery);

	List<ISearchIndexElement> search(ISearchIndexElementQuery elementQuery);

}
