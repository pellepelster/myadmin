package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.IValidationMessages;
import de.pellepelster.myadmin.client.web.modules.dictionary.BaseValidationMessages;
import de.pellepelster.myadmin.client.web.modules.dictionary.ValidationMessages;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class RootElementValidationMessages extends BaseValidationMessages implements IValidationMessages
{

	private Map<BaseDictionaryElement<?>, ValidationMessages> validationMessages = new HashMap<BaseDictionaryElement<?>, ValidationMessages>();

	public RootElementValidationMessages(Map<BaseDictionaryElement<?>, ValidationMessages> validationMessages)
	{
		super();
		this.validationMessages = validationMessages;
	}


	private List<IValidationMessage> getAllValidationMessages()
	{
		List<IValidationMessage> result = new ArrayList<IValidationMessage>();
		
		for(Map.Entry<BaseDictionaryElement<?>, ValidationMessages> validationMessageEntry : validationMessages.entrySet())
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
		Iterator<ValidationMessages> iterator = validationMessages.values().iterator();
		
		while(iterator.hasNext())
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

}
