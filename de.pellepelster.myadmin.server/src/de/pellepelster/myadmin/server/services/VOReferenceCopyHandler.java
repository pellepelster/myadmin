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

import java.util.List;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.db.copy.FieldDescriptor;
import de.pellepelster.myadmin.db.copy.IFieldCopyHandler;

public class VOReferenceCopyHandler implements IFieldCopyHandler
{
	private final VOMetaDataService metaDataService;
	
	private final SimpleVOToXmlCopyBean simpleVOToXmlCopyBean;

	public VOReferenceCopyHandler(VOMetaDataService metaDataService, SimpleVOToXmlCopyBean simpleVOToXmlCopyBean)
	{
		super();
		this.metaDataService = metaDataService;
		this.simpleVOToXmlCopyBean = simpleVOToXmlCopyBean;
	}

	/** {@inheritDoc} */
	@Override
	public boolean check(FieldDescriptor fieldDescriptor)
	{
		return IBaseVO.class.isAssignableFrom(fieldDescriptor.getSourceType()) || List.class.isAssignableFrom(fieldDescriptor.getSourceType());
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void copy(FieldDescriptor fieldDescriptor, Object sourceObject, Object targetObject) throws Exception
	{
		
		if (fieldDescriptor.getSourceValue() instanceof List)
		{
			List<IBaseVO> sourceList = (List<IBaseVO>) fieldDescriptor.getSourceValue();
			
			if (!sourceList.isEmpty())
			{
				Class<?> xmlReferenceWrapperClass = metaDataService.getXmlReferenceListWrapperClassForVOClass(sourceList.get(0).getClass());
				Class<?> xmlReferenceClass = metaDataService.getXmlReferenceClassForVOClass(sourceList.get(0).getClass());

				Object xmlReferenceWrapper = ConstructorUtils.invokeConstructor(xmlReferenceWrapperClass, new Object[0]);

				List targetList = XmlService.getFirstList(xmlReferenceWrapper);

				for (IBaseVO vo : sourceList)
				{
					Object xmlReference = simpleVOToXmlCopyBean.copyObject(vo, xmlReferenceClass, new String[] { IBaseVO.FIELD_ID.getAttributeName() });
					targetList.add(xmlReference);
				}

				PropertyUtils.setProperty(targetObject, fieldDescriptor.getFieldName(), xmlReferenceWrapper);
			}
			
			
		} else if (fieldDescriptor.getSourceValue() instanceof IBaseVO)
		{
			Class<?> xmlReferenceClass = metaDataService.getXmlReferenceClassForVOClass(fieldDescriptor.getSourceType());
			Object xmlReference = simpleVOToXmlCopyBean.copyObject(fieldDescriptor.getSourceValue(), xmlReferenceClass, new String[] { IBaseVO.FIELD_ID.getAttributeName() });

			PropertyUtils.setProperty(targetObject, fieldDescriptor.getFieldName(), xmlReference);

		}

}

}
