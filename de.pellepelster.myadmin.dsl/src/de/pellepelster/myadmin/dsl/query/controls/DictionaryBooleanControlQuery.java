package de.pellepelster.myadmin.dsl.query.controls;

import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryBooleanControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class DictionaryBooleanControlQuery extends DictionaryControlQuery<DictionaryBooleanControl>
{
	protected DictionaryBooleanControlQuery(DictionaryBooleanControl dictionaryBooleanControl)
	{
		super(dictionaryBooleanControl, MyAdminDslPackage.Literals.DICTIONARY_BOOLEAN_CONTROL__REF);
	}

}
