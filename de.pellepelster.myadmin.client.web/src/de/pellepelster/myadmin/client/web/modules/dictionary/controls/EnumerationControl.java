package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import java.util.Map;

import de.pellepelster.myadmin.client.base.messages.IMessage;
import de.pellepelster.myadmin.client.base.messages.ValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IEnumerationControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IEnumerationControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

<<<<<<< HEAD
public class EnumerationControl extends BaseDictionaryControl<IEnumerationControlModel, Object> implements IEnumerationControl
=======
public class EnumerationControl extends BaseDictionaryControl<IEnumerationControlModel, Object>  implements IEnumerationControl
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
{

	public EnumerationControl(IEnumerationControlModel enumerationControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(enumerationControlModel, parent);
	}

	@Override
	public String format()
	{
		if (getValue() == null)
		{
			return "";
		}
		else
		{
<<<<<<< HEAD
			return getModel().getEnumeration().get(getValue().toString()).toString();
=======
			return getModel().getEnumeration().get(getValue()).toString();
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
		}
	}

	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		for (Map.Entry<String, String> enumEntry : getModel().getEnumeration().entrySet())
		{
			if (enumEntry.getValue().equals(valueString))
			{
				return new ParseResult(enumEntry.getKey());
			}
		}

		return new ParseResult(new ValidationMessage(IMessage.SEVERITY.ERROR, EnumerationControl.class.getName(),
				MyAdmin.MESSAGES.enumerationParseError(valueString)));
	}

}
