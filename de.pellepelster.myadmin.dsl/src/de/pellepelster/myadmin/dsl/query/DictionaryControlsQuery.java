package de.pellepelster.myadmin.dsl.query;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryControl;
import de.pellepelster.myadmin.dsl.query.controls.DictionaryControlQuery;

public class DictionaryControlsQuery<C extends DictionaryControl> extends BaseEObjectCollectionQuery<C>
{
	public DictionaryControlsQuery(List<C> dictionaryControls)
	{
		super(dictionaryControls);
	}

	public List<DictionaryControlQuery<?>> getDictionaryControlsQueries()
	{
		List<DictionaryControlQuery<?>> queries = new ArrayList<DictionaryControlQuery<?>>();

		for (DictionaryControl dictionaryControl : getList())
		{
			queries.add(DictionaryControlQuery.create(dictionaryControl));
		}

		return queries;
	}

	public DictionaryControlQuery<?> getSingleDictionaryControlQuery()
	{
		DictionaryControl dictionaryControl = getSingleResult();
		return DictionaryControlQuery.create(dictionaryControl);

	}
}
