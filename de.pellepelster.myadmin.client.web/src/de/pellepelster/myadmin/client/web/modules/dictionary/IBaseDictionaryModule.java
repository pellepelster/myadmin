package de.pellepelster.myadmin.client.web.modules.dictionary;

import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;

public interface IBaseDictionaryModule
{
	<ElementType> ElementType getElement(DictionaryDescriptor<ElementType> controlDescriptor);
}
