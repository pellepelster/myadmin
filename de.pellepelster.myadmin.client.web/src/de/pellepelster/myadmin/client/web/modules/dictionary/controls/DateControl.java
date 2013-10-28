package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

import de.pellepelster.myadmin.client.base.messages.IMessage;
import de.pellepelster.myadmin.client.base.messages.ValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IDateControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class DateControl extends BaseDictionaryControl<IDateControlModel, Date>
{

	public DateControl(IDateControlModel dateControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(dateControlModel, parent);
	}

	@Override
	public String format()
	{

		if (getValue() != null && getValue() instanceof Date)
		{
			DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
			return dateFormat.format(getValue());
		}
		else
		{
			return super.format();
		}
	}
	
	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		try
		{
			return new ParseResult(SimpleDateFormat.getInstance().parse(valueString));
		}
		catch (ParseException e)
		{
			return new ParseResult(new ValidationMessage(IMessage.SEVERITY.ERROR, IntegerControl.class.getName(),
					MyAdmin.MESSAGES.floatValidationError(valueString)));
		}
	}

}
