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

import javax.annotation.Resource;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.NaturalKey;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterFactory;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.messages.ValidationMessage;
import de.pellepelster.myadmin.client.base.util.CollectionUtils;
import de.pellepelster.myadmin.db.IBaseVODAO;

/**
 * Checks all {@link IBaseVO} derived Vo's whether they contain values in
 * {@link NaturalKey} annotated properties that are already present in the
 * database
 * 
 * @author pelle
 * 
 */
public class NaturalKeyValidator implements IValidator
{
	@Resource
	private IBaseVODAO baseVODAO;

	/** {@inheritDoc} */
	@Override
	public boolean canValidate(Object o)
	{
		return IBaseVO.class.isAssignableFrom(o.getClass());
	}

	protected void setBaseVODAO(IBaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

	@Override
	public List<IValidationMessage> validate(Object o)
	{
		IBaseVO vo = (IBaseVO) o;

		List<IValidationMessage> result = new ArrayList<IValidationMessage>();

		for (IAttributeDescriptor<?> attributeDescriptor : new AnnotationIterator(vo.getClass(), NaturalKey.class))
		{
			@SuppressWarnings("unchecked")
			GenericFilterVO<IBaseVO> genericFilterVO = (GenericFilterVO<IBaseVO>) GenericFilterFactory.createGenericFilter(vo.getClass(),
					attributeDescriptor.getAttributeName(), vo.get(attributeDescriptor.getAttributeName()));

			List<IBaseVO> filterResult = this.baseVODAO.filter(genericFilterVO);

			if (filterResult.size() > 1 || (filterResult.size() == 1 && filterResult.get(0).getOid() != vo.getOid()))
			{
				result.add(new ValidationMessage(ValidatorMessages.NATURAL_KEY, CollectionUtils.getMap(IValidationMessage.ATTRIBUTE_CONTEXT_KEY,
						attributeDescriptor.getAttributeName(), IValidationMessage.VALUE_CONTEXT_KEY, vo.get(attributeDescriptor.getAttributeName()),
						IValidationMessage.VOCLASS_CONTEXT_KEY, vo.getClass().getSimpleName())));
			}
		}

		return result;
	}
}
