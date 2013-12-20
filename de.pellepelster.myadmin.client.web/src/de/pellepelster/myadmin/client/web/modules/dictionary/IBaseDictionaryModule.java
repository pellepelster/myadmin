package de.pellepelster.myadmin.client.web.modules.dictionary;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel;

public interface IBaseDictionaryModule
{
	<ElementType> ElementType getElement(BaseModel<ElementType> baseModel);
}
