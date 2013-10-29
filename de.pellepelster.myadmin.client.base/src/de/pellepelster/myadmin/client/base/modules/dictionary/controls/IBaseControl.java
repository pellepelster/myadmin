package de.pellepelster.myadmin.client.base.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.IValidationMessages;

public interface IBaseControl<ValueType>
{
	void setValue(ValueType value);

	ValueType getValue();

	String format();

	void parseValue(String valueString);

	boolean isMandatory();

	IValidationMessages getValidationMessages();

}
