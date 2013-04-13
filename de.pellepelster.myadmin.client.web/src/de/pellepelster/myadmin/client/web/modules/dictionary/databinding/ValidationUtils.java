/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package de.pellepelster.myadmin.client.web.modules.dictionary.databinding;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.messages.IMessage;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDatabindingAwareModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.util.CollectionUtils;
import de.pellepelster.myadmin.client.base.util.MessageFormat;

/**
 * Utilities for validator handling
 * 
 * @author pelle
 * 
 */
public final class ValidationUtils
{

	/**
	 * Creates the validation message for a list of error messages
	 * 
	 * @param validationMessages
	 * @return
	 */
	public static String getValidationMessageString(List<IValidationMessage> validationMessages, IBaseControlModel baseControlModel)
	{

		String result = "";
		String delimiter = "";

		for (IValidationMessage validationMessage : validationMessages)
		{
			result += delimiter
					+ MessageFormat.format(validationMessage.getHumanMessage(),
							CollectionUtils.getMap(IBaseControlModel.EDITOR_LABEL_MESSAGE_KEY, baseControlModel.getEditorLabel()));
			delimiter = "\r\n";
		}

		return result;

	}

	public static List<IValidationMessage> validate(List<IValidator> validators, Object value, IDatabindingAwareModel databindingAwareModel)
	{
		List<IValidationMessage> validationMessages = new ArrayList<IValidationMessage>();

		for (IValidator validator : validators)
		{
			validationMessages.addAll(validator.validate(value, databindingAwareModel));
		}

		return validationMessages;
	}

	/**
	 * Returns the validation status with the highest priority
	 * 
	 * @param validationMessages
	 * @return
	 */
	public static IMessage.SEVERITY getSeverity(List<IValidationMessage> validationMessages)
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

	public static boolean hasError(IMessage.SEVERITY severity)
	{
		return severity.getOrder() >= IMessage.SEVERITY.ERROR.getOrder();
	}

	public static boolean hasError(IValidationMessage validationMessage)
	{
		return hasError(validationMessage.getSeverity());
	}

	private ValidationUtils()
	{
	}

	public static boolean hasError(List<IValidationMessage> validationMessages)
	{
		for (IValidationMessage validationMessage : validationMessages)
		{
			if (ValidationUtils.hasError(validationMessage))
			{
				return true;
			}
		}

		return false;
	}

	public static String getAttributeContext(IValidationMessage validationMessage)
	{
		return String.valueOf(validationMessage.getContext().get(IValidationMessage.ATTRIBUTE_CONTEXT_KEY));
	}

}
