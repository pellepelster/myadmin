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

import de.pellepelster.gwt.commons.client.util.XPathUtil;
import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.Mandatory;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.messages.ValidationMessage;
import de.pellepelster.myadmin.client.base.util.Collections;
import de.pellepelster.myadmin.db.util.BeanUtil;

public class MandatoryValidator implements IValidator
{
	/** {@inheritDoc} */
	@Override
	public boolean canValidate(Object o)
	{
		return IBaseVO.class.isAssignableFrom(o.getClass());
	}

	@Override
	public List<IValidationMessage> validate(Object o)
	{
		return validateInternal(o, "");
	}

	public List<IValidationMessage> validateInternal(Object o, String parentPath)
	{
		IBaseVO vo = (IBaseVO) o;

		List<IValidationMessage> result = new ArrayList<IValidationMessage>();

		for (IAttributeDescriptor<?> attributeDescriptor : new AnnotationIterator(vo.getClass(), Mandatory.class))
		{

			String fullAttributePath = XPathUtil.combine(parentPath, attributeDescriptor.getAttributeName());

			if (attributeDescriptor.getListAttributeType() == null)
			{
				if (vo.get(attributeDescriptor.getAttributeName()) == null)
				{
					result.add(new ValidationMessage(ValidatorMessages.MANDATORY_ATTRIBUTE, Collections.getMap(IValidationMessage.ATTRIBUTE_CONTEXT_KEY,
							fullAttributePath, IValidationMessage.VOCLASS_CONTEXT_KEY, vo.getClass().getSimpleName())));
				}
			}
			else
			{
				@SuppressWarnings("unchecked")
				List<IBaseVO> list = (List<IBaseVO>) vo.get(attributeDescriptor.getAttributeName());

				if (list.isEmpty())
				{
					result.add(new ValidationMessage(ValidatorMessages.MANDATORY_LIST, Collections.getMap(IValidationMessage.ATTRIBUTE_CONTEXT_KEY,
							fullAttributePath, IValidationMessage.VOCLASS_CONTEXT_KEY, vo.getClass().getSimpleName())));
				}
			}
		}

		for (IAttributeDescriptor<?> attributeDescriptor : BeanUtil.getAttributeDescriptors(vo.getClass()))
		{
			if (attributeDescriptor.getListAttributeType() != null)
			{
				@SuppressWarnings("unchecked")
				List<IBaseVO> list = (List<IBaseVO>) vo.get(attributeDescriptor.getAttributeName());

				if (!list.isEmpty())
				{
					for (IBaseVO listItem : list)
					{
						result.addAll(validateInternal(listItem,
								XPathUtil.combine(parentPath, attributeDescriptor.getAttributeName()) + String.format("[oid=%d]", listItem.getOid())));
					}
				}
			}
		}

		return result;
	}

}
