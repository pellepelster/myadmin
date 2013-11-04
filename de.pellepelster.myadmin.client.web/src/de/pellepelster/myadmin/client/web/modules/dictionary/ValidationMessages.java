package de.pellepelster.myadmin.client.web.modules.dictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.IValidationMessages;

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
		return hasError(validationMessages.iterator());
	}

	@Override
	public int count()
	{
		return this.validationMessages.size();
	}

}
