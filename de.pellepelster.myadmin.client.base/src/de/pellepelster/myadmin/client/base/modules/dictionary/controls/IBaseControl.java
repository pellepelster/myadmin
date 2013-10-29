package de.pellepelster.myadmin.client.base.modules.dictionary.controls;

<<<<<<< HEAD
import de.pellepelster.myadmin.client.base.modules.dictionary.IValidationMessages;
=======
import java.util.List;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86

public interface IBaseControl<ValueType>
{
	void setValue(ValueType value);

	ValueType getValue();

	String format();
<<<<<<< HEAD

	void parseValue(String valueString);

	boolean isMandatory();

	IValidationMessages getValidationMessages();

=======
	
	void parseValue(String valueString);

	boolean isMandatory();
	
	List<IValidationMessage> getValidationMessages();
	
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
}
