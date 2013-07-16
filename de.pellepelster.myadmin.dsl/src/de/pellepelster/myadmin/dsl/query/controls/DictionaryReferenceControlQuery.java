package de.pellepelster.myadmin.dsl.query.controls;

import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryReferenceControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class DictionaryReferenceControlQuery extends DictionaryControlQuery<DictionaryReferenceControl>
{
	protected DictionaryReferenceControlQuery(DictionaryReferenceControl dictionaryReferenceControl)
	{
		super(dictionaryReferenceControl, MyAdminDslPackage.Literals.DICTIONARY_REFERENCE_CONTROL__REF);
	}

}
