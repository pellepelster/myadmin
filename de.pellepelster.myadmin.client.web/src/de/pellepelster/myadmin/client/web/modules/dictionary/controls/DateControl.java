package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

import de.pellepelster.myadmin.client.base.messages.IMessage;
import de.pellepelster.myadmin.client.base.messages.ValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IDateControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IDateControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class DateControl extends BaseDictionaryControl<IDateControlModel, Date> implements IDateControl
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

	public native String parseDateNative(String dateString) /*-{
															var longDate = Date.parse(dateString);
															return longDate.toString();
															}-*/;

	public Date parseDate(String dateString)
	{
		String longDateString = parseDateNative(dateString);
		long longDate = Long.parseLong(longDateString);

		return new Date(longDate);
	}

	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		try
		{
			return new ParseResult(parseDate(valueString));
		}
		catch (Exception e)
		{
			return new ParseResult(new ValidationMessage(IMessage.SEVERITY.ERROR, DateControl.class.getName(), MyAdmin.MESSAGES.dateParseError(valueString)));
		}
	}

}
