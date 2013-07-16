package de.pellepelster.myadmin.dsl.query.controls;

import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryEnumerationControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class DictionaryEnumerationControlQuery extends DictionaryControlQuery<DictionaryEnumerationControl>
{
	protected DictionaryEnumerationControlQuery(DictionaryEnumerationControl dictionaryEnumerationControl)
	{
		super(dictionaryEnumerationControl, MyAdminDslPackage.Literals.DICTIONARY_ENUMERATION_CONTROL__REF);
	}

}
