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

import de.pellepelster.myadmin.client.base.db.vos.IValidationMessage;
import de.pellepelster.myadmin.client.base.db.vos.VALIDATION_STATUS;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDatabindingAwareModel;

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
	public static String getValidationMessageString(List<IValidationMessage> validationMessages)
	{

		String result = "";
		String delimiter = "";

		for (IValidationMessage validationMessage : validationMessages)
		{
			result += delimiter + validationMessage.getMessage();
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
	public static VALIDATION_STATUS getValidationStatus(List<IValidationMessage> validationMessages)
	{

		for (IValidationMessage validationMessage : validationMessages)
		{
			if (validationMessage.getStatus() != VALIDATION_STATUS.OK)
			{
				return validationMessage.getStatus();
			}
		}

		return VALIDATION_STATUS.OK;
	}

	private ValidationUtils()
	{
	}

}
