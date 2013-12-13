package de.pellepelster.myadmin.client.base.modules.dictionary;

import java.util.List;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.messages.IValidationMessages;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseRootModel;

public interface IBaseRootElement<ModelType extends IBaseRootModel> extends IBaseDictionaryElement<ModelType>
{

	IValidationMessages getValidationMessages(IBaseDictionaryElement<?> baseDictionaryElement);

	void addValidationMessages(IBaseDictionaryElement<?> baseDictionaryElement, List<IValidationMessage> elementValidationMessages);

	void clearValidationMessages(IBaseDictionaryElement<?> baseDictionaryElement);
}
