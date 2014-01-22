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

import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.messages.ValidationMessage;
import de.pellepelster.myadmin.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import de.pellepelster.myadmin.client.base.util.CollectionUtils;
import de.pellepelster.myadmin.client.web.modules.dictionary.BaseDictionaryEditorModule;
import de.pellepelster.myadmin.client.web.services.IHierachicalService;

public class HierarchicalMandatoryValidator implements IValidator
{

	@Autowired
	private IHierachicalService hierachicalService;

	/** {@inheritDoc} */
	@Override
	public boolean canValidate(Object o)
	{
		return IHierarchicalVO.class.isAssignableFrom(o.getClass());
	}

	@Override
	public List<IValidationMessage> validate(Object o)
	{
		List<IValidationMessage> result = new ArrayList<IValidationMessage>();

		IHierarchicalVO hierarchicalVO = (IHierarchicalVO) o;

		if (hierarchicalVO.getData().containsKey(BaseDictionaryEditorModule.EDITORDICTIONARYNAME_PARAMETER_ID))
		{
			String dictionaryId = (String) hierarchicalVO.getData().get(BaseDictionaryEditorModule.EDITORDICTIONARYNAME_PARAMETER_ID);

			if (hierarchicalVO.getParent() == null && !HierarchicalConfigurationVO.isRootDictionary(dictionaryId, this.hierachicalService.getConfigurations()))
			{
				result.add(new ValidationMessage(ValidatorMessages.MANDATORY_ATTRIBUTE, CollectionUtils.getMap(IValidationMessage.ATTRIBUTE_CONTEXT_KEY,
						IHierarchicalVO.FIELD_PARENT.getAttributeName(), IValidationMessage.VOCLASS_CONTEXT_KEY, hierarchicalVO.getClass().getSimpleName())));

			}

		}

		return result;
	}

	public void setHierachicalService(IHierachicalService hierachicalService)
	{
		this.hierachicalService = hierachicalService;
	}
}
