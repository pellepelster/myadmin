package de.pellepelster.myadmin.client.web.modules.dictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.messages.IValidationMessages;
import de.pellepelster.myadmin.client.base.util.MessageFormat;

public class ValidationMessages extends BaseValidationMessages implements IValidationMessages
{
	private List<IValidationMessage> validationMessages = new ArrayList<IValidationMessage>();

	@Override
	public Iterator<IValidationMessage> iterator()
	{
		return this.validationMessages.iterator();
	}

	public void addValidationMessage(IValidationMessage validationMessage)
	{
		this.validationMessages.add(validationMessage);
	}

	public void clear()
	{
		this.validationMessages.clear();
	}

	public void addAll(List<IValidationMessage> validationMessages)
	{
		this.validationMessages.addAll(validationMessages);
	}

	@Override
	public boolean hasErrors()
	{
		return hasError(this.validationMessages.iterator());
	}

	@Override
	public int count()
	{
		return this.validationMessages.size();
	}

	@Override
	public String getValidationMessageString(Map<String, Object> context)
	{

		String result = "";
		String delimiter = "";

		for (IValidationMessage validationMessage : this.validationMessages)
		{
			result += delimiter + MessageFormat.format(validationMessage.getHumanMessage(), context);
			delimiter = "\r\n";
		}

		return result;

	}
}
