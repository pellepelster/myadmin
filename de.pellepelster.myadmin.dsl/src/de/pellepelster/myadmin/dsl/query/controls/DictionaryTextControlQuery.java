package de.pellepelster.myadmin.dsl.query.controls;

import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryTextControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class DictionaryTextControlQuery extends DictionaryControlQuery<DictionaryTextControl>
{
	protected DictionaryTextControlQuery(DictionaryTextControl dictionaryTextControl)
	{
		super(dictionaryTextControl, MyAdminDslPackage.Literals.DICTIONARY_TEXT_CONTROL__REF);
	}

}
