package de.pellepelster.myadmin.dsl.query.controls;

import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryDateControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class DictionaryDateControlQuery extends DictionaryControlQuery<DictionaryDateControl>
{
	protected DictionaryDateControlQuery(DictionaryDateControl dictionaryDateControl)
	{
		super(dictionaryDateControl, MyAdminDslPackage.Literals.DICTIONARY_DATE_CONTROL__REF);
	}

}
