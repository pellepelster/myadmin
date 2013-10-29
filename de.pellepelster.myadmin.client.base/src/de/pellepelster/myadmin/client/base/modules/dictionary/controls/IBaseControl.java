package de.pellepelster.myadmin.client.base.modules.dictionary.controls;

import java.util.List;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;

public interface IBaseControl<ValueType>
{
	void setValue(ValueType value);

	ValueType getValue();

	String format();
	
	void parseValue(String valueString);

	boolean isMandatory();
	
	List<IValidationMessage> getValidationMessages();
	
}
