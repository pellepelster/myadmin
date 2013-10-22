package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.messages.IMessage;
import de.pellepelster.myadmin.client.base.messages.ValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IIntegerControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IIntegerControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class IntegerControl extends BaseDictionaryControl<IIntegerControlModel, Integer>  implements IIntegerControl
{

	public IntegerControl(IIntegerControlModel integerControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(integerControlModel, parent);
	}

	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		try
		{
			return new ParseResult(new Integer(valueString));
		}
		catch (NumberFormatException e)
		{
			return new ParseResult(new ValidationMessage(IMessage.SEVERITY.ERROR, IntegerControl.class.getName(),
					MyAdmin.MESSAGES.integerParseError(valueString)));
		}
	}

}
