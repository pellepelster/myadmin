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
package de.pellepelster.myadmin.client.gwt.modules.dictionary.controls;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.messages.IMessage;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.messages.ValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDatabindingAwareModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBigDecimalControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BigDecimalControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.IValidator;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.validator.BaseValidator;

/**
 * control factory for float controls
 * 
 * @author pelle
 * 
 */
public class BigDecimalControlFactory extends BaseControlFactory<IBigDecimalControlModel, BigDecimalControl>
{

	private static class BigDecimalValidator extends BaseValidator
	{

		/** {@inheritDoc} */
		@SuppressWarnings("unchecked")
		@Override
		public List<IValidationMessage> validate(Object value, IDatabindingAwareModel databindingAwareModel)
		{
			if (value != null && value instanceof String)
			{
				String valueString = value.toString();

				if (valueString.isEmpty())
				{
					return Collections.EMPTY_LIST;
				}
				else
				{
					try
					{
						new BigDecimal(value.toString());
						return Collections.EMPTY_LIST;
					}
					catch (NumberFormatException e)
					{
						return resultListHelper(new ValidationMessage(IMessage.SEVERITY.ERROR, BigDecimalControlFactory.class.getName(),
								MyAdmin.MESSAGES.floatValidationError(value.toString())));
					}
				}
			}
			else
			{
				throw getValidationException(value);
			}
		}
	}

	private static final BigDecimalValidator BIGDECIMAL_VALIDATOR = new BigDecimalValidator();

	private static final List<IValidator> VALIDATORS = Arrays.asList(new IValidator[] { BIGDECIMAL_VALIDATOR });

	/** {@inheritDoc} */
	@Override
	public Widget createControl(BigDecimalControl bigDecimalControl, LAYOUT_TYPE layoutType)
	{
		return new GwtBigDecimalControl(bigDecimalControl);
	}

	/** {@inheritDoc} */
	@Override
	public List<IValidator> createValidators(BigDecimalControl bigDecimalControl)
	{
		return addValidators(bigDecimalControl, VALIDATORS);
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl<?, ?> baseControlModel)
	{
		return baseControlModel instanceof BigDecimalControl;
	}

}
