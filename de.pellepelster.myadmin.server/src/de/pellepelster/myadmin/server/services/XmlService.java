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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.db.copy.FieldDescriptor;
import de.pellepelster.myadmin.db.copy.FieldIterator;
import de.pellepelster.myadmin.server.base.xml.XmlVOMapping;

@Component
@SuppressWarnings("rawtypes")
public class XmlService
{
	private final static Logger LOG = Logger.getLogger(XmlService.class);

	public static List<Object> getFirstList(Object xmlListWrapper)
	{
		for (FieldDescriptor fieldDescriptor : new FieldIterator(xmlListWrapper))
		{
			if (fieldDescriptor.getSourceType().equals(List.class))
			{
				List<Object> wrappedList = (List) fieldDescriptor.getSourceValue();
				return wrappedList;
			}
		}

		return null;
	}

	@Autowired
	private XmlToVOCopyBean xmToVolCopyBean;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private VOToXmlCopyBean voToXmlCopyBean;

	@Autowired
	private VOMetaDataService metaDataService;

	public Class<?> detectXmlClass(String xmlString)
	{
		try
		{
			Pattern regex = Pattern.compile("</?[a-zA-Z][a-zA-Z0-9]*[^<>]*>");
			Matcher regexMatcher = regex.matcher(xmlString);

			String rootXmlElementName = null;

			while (regexMatcher.find())
			{
				rootXmlElementName = regexMatcher.group();
				break;
			}

			rootXmlElementName = StringUtils.removeEnd(rootXmlElementName, ">");
			rootXmlElementName = StringUtils.removeEnd(rootXmlElementName, "/");
			rootXmlElementName = StringUtils.removeStart(rootXmlElementName, "<");
			rootXmlElementName = rootXmlElementName.split(" ")[0];

			Class<?> xmlClass = this.metaDataService.getXmlRootClassByElementName(rootXmlElementName);

			return xmlClass;

		}
		catch (PatternSyntaxException ex)
		{
			// Syntax error in the regular expression
		}

		return null;
	}

	private JAXBContext jaxbContext;

	private JAXBContext getJaxbContext() throws JAXBException
	{
		if (this.jaxbContext == null)
		{
			this.jaxbContext = JAXBContext.newInstance(this.metaDataService.getXmlRootClasses().toArray(new Class<?>[0]));
		}

		return this.jaxbContext;
	}

	public <T extends IBaseVO> void export(Class<? extends IBaseVO> voClass, List<T> vos, OutputStream outputStream)
	{
		try
		{
			Object xmlListWrapper = vosToXmlListWrapper(voClass, vos);

			Marshaller marshaller = getJaxbContext().createMarshaller();

			marshaller.marshal(xmlListWrapper, outputStream);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				outputStream.close();
			}
			catch (IOException ioException)
			{
				throw new RuntimeException(ioException);
			}
		}
	}

	public Class<? extends IBaseVO> getVOClassForXmlClass(Class<?> xmlClass)
	{
		XmlVOMapping xmlVOMapping = AnnotationUtils.findAnnotation(xmlClass, XmlVOMapping.class);

		if (xmlVOMapping == null)
		{
			throw new RuntimeException(String.format("class '%s' has no xml vo mapping information", xmlClass.getName()));
		}

		return xmlVOMapping.voClass();
	}

	private <T> void handleReference(T reference)
	{
	}

	private <T> void handleReferenceWrapper(T referenceWrapper)
	{
		try
		{
			for (Map.Entry<String, Object> entry : ((Map<String, Object>) PropertyUtils.describe(referenceWrapper)).entrySet())
			{
				Object propertyValue = entry.getValue();

				if (propertyValue instanceof List)
				{
					List list = (List) propertyValue;

					for (Object listElement : list)
					{
						if (listElement != null)
						{
							handleReference(listElement);
						}
					}

					continue;
				}

				handleReference(propertyValue);
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(String.format("error resolving reference object '%s'", referenceWrapper.getClass().getName()));
		}

	}

	private <T> void importSingleXmlObject(T xmlObject)
	{
		XmlVOMapping xmlVOMapping = AnnotationUtils.findAnnotation(xmlObject.getClass(), XmlVOMapping.class);

		if (xmlVOMapping != null)
		{
			IBaseVO vo = (IBaseVO) this.xmToVolCopyBean.copyObject(xmlObject, xmlVOMapping.voClass());
			this.baseEntityService.create(vo);
		}

		/*
		 * XmlVOMapping xmlAttributeVOMapping =
		 * AnnotationUtils.findAnnotation(sourceValue.getClass(),
		 * XmlVOMapping.class);
		 * 
		 * if (xmlAttributeVOMapping != null &&
		 * xmlAttributeVOMapping.isReferenceWrapper()) {
		 * handleReferenceWrapper(sourceValue); }
		 */
	}

	public void importXml(Class<?> xmlClass, InputStream inputStream)
	{
		Object xmlObject = unmarshal(xmlClass, inputStream);

		XmlVOMapping xmlVOMapping = AnnotationUtils.findAnnotation(xmlClass, XmlVOMapping.class);

		if (xmlVOMapping == null)
		{
			throw new RuntimeException(String.format("class '%s' has no xml vo mapping information", xmlClass.getName()));
		}

		if (xmlVOMapping.isListWrapper())
		{
			importXmlListWrapperObject(xmlObject);
		}
		else
		{
			importSingleXmlObject(xmlObject);
		}
	}

	private <T> void importXmlListWrapperObject(T xmlListWrapperObject)
	{
		List list = getFirstList(xmlListWrapperObject);

		for (Object listElement : list)
		{
			if (listElement != null)
			{
				importSingleXmlObject(listElement);
			}
		}
	}

	public void setMetaDataService(VOMetaDataService metaDataService)
	{
		this.metaDataService = metaDataService;
	}

	public void setXmToVolCopyBean(XmlToVOCopyBean xmToVolCopyBean)
	{
		this.xmToVolCopyBean = xmToVolCopyBean;
	}

	public Object unmarshal(Class xmlClass, InputStream inputStream)
	{
		try
		{
			Unmarshaller u = getJaxbContext().createUnmarshaller();

			return u.unmarshal(inputStream);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public <T extends IBaseVO> Object vosToXmlListWrapper(Class<? extends IBaseVO> voClass, List<T> vos)
	{
		List<Object> xmlObjects = vosToXmlObjectList(voClass, vos);

		Class<?> xmlListWrapperClass = this.metaDataService.getXmlListWrapperClassForVOClass(voClass);

		Object xmlListWrapper = null;

		try
		{
			xmlListWrapper = ConstructorUtils.invokeConstructor(xmlListWrapperClass, new Object[0]);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		getFirstList(xmlListWrapper).addAll(xmlObjects);

		return xmlListWrapper;
	}

	public <T extends IBaseVO> List<Object> vosToXmlObjectList(Class<? extends IBaseVO> voClass, List<T> vos)
	{
		if (vos.isEmpty())
		{
			return new ArrayList<Object>();
		}

		Class<?> xmlClass = this.metaDataService.getXmlClassForVOClass(voClass);
		List<Object> xmlObjects = new ArrayList<Object>();

		for (T vo : vos)
		{
			Object xmlObject = this.voToXmlCopyBean.copyObject(vo, xmlClass);
			xmlObjects.add(xmlObject);
		}

		return xmlObjects;
	}

}
