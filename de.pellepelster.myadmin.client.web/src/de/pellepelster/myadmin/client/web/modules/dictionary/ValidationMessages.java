package de.pellepelster.myadmin.client.web.modules.dictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.pellepelster.myadmin.client.base.messages.IMessage;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.IValidationMessages;

public class ValidationMessages implements IValidationMessages
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

	public IMessage.SEVERITY getSeverity(List<IValidationMessage> validationMessages)
	{
		IMessage.SEVERITY severity = IMessage.SEVERITY.NONE;

		for (IValidationMessage validationMessage : validationMessages)
		{
			if (validationMessage.getSeverity().getOrder() > severity.getOrder())
			{
				severity = validationMessage.getSeverity();
			}
		}

		return severity;
	}

	private boolean hasError(IMessage.SEVERITY severity)
	{
		return severity.getOrder() >= IMessage.SEVERITY.ERROR.getOrder();
	}

	private boolean hasError(IValidationMessage validationMessage)
	{
		return hasError(validationMessage.getSeverity());
	}

	@Override
	public boolean hasError()
	{
		for (IValidationMessage validationMessage : this.validationMessages)
		{
			if (hasError(validationMessage))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public int count()
	{
		return this.validationMessages.size();
	}

}
