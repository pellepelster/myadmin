package de.pellepelster.myadmin.client.base.modules.dictionary.controls;

public interface IBaseControl<ValueType>
{
	void setValue(ValueType value);

	ValueType getValue();

	String format();
	
	void parseValue(String valueString);

}
