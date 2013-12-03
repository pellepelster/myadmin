package de.pellepelster.myadmin.dsl.query.controls;

import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryFileControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class DictionaryFileControlQuery extends DictionaryControlQuery<DictionaryFileControl>
{
	protected DictionaryFileControlQuery(DictionaryFileControl dictionaryFileControl)
	{
		super(dictionaryFileControl, MyAdminDslPackage.Literals.DICTIONARY_FILE_CONTROL__REF);
	}

}
