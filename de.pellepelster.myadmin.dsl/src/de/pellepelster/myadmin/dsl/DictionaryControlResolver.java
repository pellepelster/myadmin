package de.pellepelster.myadmin.dsl;

import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryControl;
import de.pellepelster.myadmin.dsl.query.controls.DictionaryControlQuery;

public class DictionaryControlResolver
{

	public static String resolveControlName(DictionaryControl dictionaryControl)
	{
		return DictionaryControlQuery.create(dictionaryControl).getName(true);
	}
}
