package de.pellepelster.myadmin.db.index;

import java.util.List;

public interface ISearchService
{

	void add(IDictionarySearchElement element);

	void delete(ISearchElementQuery elementQuery);

	void update(IDictionarySearchElement element);

	void deleteAll(ISearchElementQuery elementQuery);

	long getCount(ISearchElementQuery elementQuery);

	List<IDictionarySearchElement> search(String query);

}
