package de.pellepelster.myadmin.client.base.modules.dictionary;

import java.util.Map;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;

public interface IValidationMessages extends Iterable<IValidationMessage>
{
	boolean hasErrors();

	int count();

	String getValidationMessageString(Map<String, Object> context);

}
