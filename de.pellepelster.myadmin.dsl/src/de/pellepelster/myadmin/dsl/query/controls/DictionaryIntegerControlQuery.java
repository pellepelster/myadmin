package de.pellepelster.myadmin.dsl.query.controls;

import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryIntegerControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class DictionaryIntegerControlQuery extends DictionaryControlQuery<DictionaryIntegerControl>
{
	protected DictionaryIntegerControlQuery(DictionaryIntegerControl dictionaryIntegerControl)
	{
		super(dictionaryIntegerControl, MyAdminDslPackage.Literals.DICTIONARY_INTEGER_CONTROL__REF);
	}

}
