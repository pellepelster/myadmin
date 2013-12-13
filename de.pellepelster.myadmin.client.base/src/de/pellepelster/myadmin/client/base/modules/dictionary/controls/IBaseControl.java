package de.pellepelster.myadmin.client.base.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.messages.IValidationMessages;
import de.pellepelster.myadmin.client.base.modules.dictionary.IBaseDictionaryElement;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public interface IBaseControl<ValueType, ModelType extends IBaseModel> extends IBaseDictionaryElement<ModelType>
{
	void setValue(ValueType value);

	ValueType getValue();

	String format();

	void parseValue(String valueString);

	boolean isMandatory();

	IValidationMessages getValidationMessages();

}
