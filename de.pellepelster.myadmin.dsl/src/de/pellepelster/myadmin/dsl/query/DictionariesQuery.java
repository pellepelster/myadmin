package de.pellepelster.myadmin.dsl.query;

import java.util.Collection;
import java.util.List;

import de.pellepelster.myadmin.dsl.myAdminDsl.Dictionary;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class DictionariesQuery extends BaseEObjectCollectionQuery<Dictionary>
{
	public DictionariesQuery(List<Dictionary> dictionaries)
	{
		super(dictionaries);
	}

	public Collection<Dictionary> getDictionaries()
	{
		return getList();
	}

	public DictionaryQuery getByName(String dictionaryName)
	{
		return new DictionaryQuery(getSingleByEStructuralFeature(MyAdminDslPackage.Literals.DICTIONARY__NAME, dictionaryName));
	}
}
