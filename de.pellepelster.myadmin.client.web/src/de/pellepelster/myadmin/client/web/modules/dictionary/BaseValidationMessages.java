package de.pellepelster.myadmin.client.web.modules.dictionary;

import java.util.Iterator;
import java.util.List;

import de.pellepelster.myadmin.client.base.messages.IMessage;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.IValidationMessages;

public abstract class BaseValidationMessages implements IValidationMessages
{
	protected IMessage.SEVERITY getSeverity(List<IValidationMessage> validationMessages)
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

	protected boolean hasError(Iterator<IValidationMessage> validationMessageIterator)
	{
		while (validationMessageIterator.hasNext())
		{
			IValidationMessage validationMessage = validationMessageIterator.next();

			if (hasError(validationMessage))
			{
				return true;
			}

		}

		return false;
	}

}
