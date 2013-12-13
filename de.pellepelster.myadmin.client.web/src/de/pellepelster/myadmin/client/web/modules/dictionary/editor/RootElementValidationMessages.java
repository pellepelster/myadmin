package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.messages.IValidationMessages;
import de.pellepelster.myadmin.client.base.modules.dictionary.IBaseDictionaryElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.BaseValidationMessages;
import de.pellepelster.myadmin.client.web.modules.dictionary.ValidationMessages;

public class RootElementValidationMessages extends BaseValidationMessages implements IValidationMessages
{

	private Map<IBaseDictionaryElement<?>, ValidationMessages> validationMessages = new HashMap<IBaseDictionaryElement<?>, ValidationMessages>();

	public RootElementValidationMessages(Map<IBaseDictionaryElement<?>, ValidationMessages> validationMessages)
	{
		super();
		this.validationMessages = validationMessages;
	}

	private List<IValidationMessage> getAllValidationMessages()
	{
		List<IValidationMessage> result = new ArrayList<IValidationMessage>();

		for (Map.Entry<IBaseDictionaryElement<?>, ValidationMessages> validationMessageEntry : this.validationMessages.entrySet())
		{
			result.addAll(Lists.newArrayList(validationMessageEntry.getValue().iterator()));
		}

		return result;
	}

	@Override
	public Iterator<IValidationMessage> iterator()
	{
		return getAllValidationMessages().iterator();
	}

	@Override
	public boolean hasErrors()
	{
		Iterator<ValidationMessages> iterator = this.validationMessages.values().iterator();

		while (iterator.hasNext())
		{
			ValidationMessages tmpValidationMessages = iterator.next();

			if (tmpValidationMessages.hasErrors())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public int count()
	{
		return getAllValidationMessages().size();
	}

	@Override
	public String getValidationMessageString(Map<String, Object> context)
	{
		String result = "";
		Iterator<ValidationMessages> iterator = this.validationMessages.values().iterator();

		while (iterator.hasNext())
		{
			ValidationMessages tmpValidationMessages = iterator.next();

			if (tmpValidationMessages.hasErrors())
			{
				result += tmpValidationMessages.getValidationMessageString(context);
			}
		}

		return result;
	}

	@Override
	public void addValidationMessage(IValidationMessage validationMessage)
	{
		throw new RuntimeException("not implemented");
	}

}
