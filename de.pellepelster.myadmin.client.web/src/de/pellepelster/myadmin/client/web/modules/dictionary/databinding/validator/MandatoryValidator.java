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
package de.pellepelster.myadmin.client.web.modules.dictionary.databinding.validator;

import java.util.Collections;
import java.util.List;

import de.pellepelster.myadmin.client.base.messages.IMessage;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.messages.ValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IContentAwareModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;

public class MandatoryValidator extends BaseValidator
{
	private boolean isEmpty(Object value)
	{
		return value == null || value.toString().isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public List<IValidationMessage> validate(Object value, IContentAwareModel databindingAwareModel)
	{
		if (isEmpty(value) && databindingAwareModel instanceof IBaseControlModel)
		{
			IBaseControlModel baseControlModel = (IBaseControlModel) databindingAwareModel;

			return resultListHelper(new ValidationMessage(IMessage.SEVERITY.ERROR, MandatoryValidator.class.getName(),
					MyAdmin.MESSAGES.mandatoryMessage(DictionaryModelUtil.getEditorLabel(baseControlModel))));
		}
		else
		{
			return Collections.emptyList();
		}
	}
}
