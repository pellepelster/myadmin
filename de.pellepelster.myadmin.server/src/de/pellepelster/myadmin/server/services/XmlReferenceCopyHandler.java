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
package de.pellepelster.myadmin.server.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.core.annotation.AnnotationUtils;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterFactory;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.db.copy.FieldDescriptor;
import de.pellepelster.myadmin.db.copy.FieldIterator;
import de.pellepelster.myadmin.db.copy.IFieldCopyHandler;
import de.pellepelster.myadmin.server.core.xml.XmlVOMapping;

public class XmlReferenceCopyHandler implements IFieldCopyHandler
{

	private final IBaseEntityService baseEntityService;

	public XmlReferenceCopyHandler(IBaseEntityService baseEntityService)
	{
		super();
		this.baseEntityService = baseEntityService;
	}

	/** {@inheritDoc} */
	@Override
	public boolean check(FieldDescriptor fieldDescriptor)
	{
		XmlVOMapping xmlVOMapping = AnnotationUtils.findAnnotation(fieldDescriptor.getSourceType(), XmlVOMapping.class);

		return xmlVOMapping != null && (xmlVOMapping.isReference() || xmlVOMapping.isReferenceListWrapper());
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void copy(FieldDescriptor fieldDescriptor, Object sourceObject, Object targetObject) throws Exception
	{
		XmlVOMapping xmlVOMapping = AnnotationUtils.findAnnotation(fieldDescriptor.getSourceType(), XmlVOMapping.class);

		if (xmlVOMapping.isReferenceListWrapper())
		{
			Object referenceListWrapper = fieldDescriptor.getSourceValue();
			List referenceList = XmlService.getFirstList(referenceListWrapper);
			List targetList = new ArrayList();

			for (Object referenceType : referenceList)
			{
				Object reference = getByNaturalKey(xmlVOMapping.voClass(), referenceType);
				targetList.add(reference);
			}

			PropertyUtils.setProperty(targetObject, fieldDescriptor.getFieldName(), targetList);

		}
		else if (xmlVOMapping.isReference())
		{
			Object reference = getByNaturalKey(xmlVOMapping.voClass(), fieldDescriptor.getSourceValue());
			PropertyUtils.setProperty(targetObject, fieldDescriptor.getFieldName(), reference);
		}
	}

	private Object getByNaturalKey(Class<? extends IBaseVO> voClass, Object source)
	{
		@SuppressWarnings("unchecked")
		GenericFilterVO<IBaseVO> genericFilterVO = (GenericFilterVO<IBaseVO>) GenericFilterFactory.createGenericFilter(voClass).getGenericFilter();

		for (FieldDescriptor naturalKeyFieldDescriptor : new FieldIterator(source))
		{
			if (naturalKeyFieldDescriptor.getSourceValue() != null)
			{
				genericFilterVO.addCriteria(naturalKeyFieldDescriptor.getFieldName(), naturalKeyFieldDescriptor.getSourceValue());
			}
		}

		List<IBaseVO> vos = this.baseEntityService.filter(genericFilterVO);

		if (vos.size() == 1)
		{
			return vos.get(0);
		}
		else
		{
			throw new RuntimeException("natural key references more than one or no object");
		}

	}
}
