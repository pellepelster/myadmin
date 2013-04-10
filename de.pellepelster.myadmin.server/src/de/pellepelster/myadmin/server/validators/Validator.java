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
package de.pellepelster.myadmin.server.validators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;

public class Validator implements IValidator
{
	@Autowired
	private List<IValidator> validators = new ArrayList<IValidator>();

	/** {@inheritDoc} */
	@Override
	public boolean canValidate(Object o)
	{
		return true;
	}

	public List<IValidator> getValidators()
	{
		return validators;
	}

	/** {@inheritDoc} */
	@Override
	public List<IValidationMessage> validate(Object o)
	{
		List<IValidationMessage> validationMessages = new ArrayList<IValidationMessage>();

		for (IValidator validator : validators)
		{
			if (validator.canValidate(o))
			{
				validationMessages.addAll(validator.validate(o));
			}
		}

		return validationMessages;
	}
}
