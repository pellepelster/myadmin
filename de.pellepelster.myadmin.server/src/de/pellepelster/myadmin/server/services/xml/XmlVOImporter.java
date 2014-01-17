package de.pellepelster.myadmin.server.services.xml;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.util.SimpleCallback;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.db.util.BeanUtils;
import de.pellepelster.myadmin.server.base.xml.XmlElementDescriptor;
import de.pellepelster.myadmin.server.core.query.ServerGenericFilterBuilder;

@Component
public class XmlVOImporter
{
	private class VOAttributeSetteCallback implements SimpleCallback<IBaseVO>
	{
		private IBaseVO vo;

		private String attributeName;

		public VOAttributeSetteCallback(IBaseVO vo, String attributeName)
		{
			super();
			this.vo = vo;
			this.attributeName = attributeName;
		}

		@Override
		public void onCallback(IBaseVO t)
		{
			this.vo.set(this.attributeName, t);
		}
	}

	private class ListAdderCallback implements SimpleCallback<IBaseVO>
	{
		private List<IBaseVO> list;

		private String attributeName;

		public ListAdderCallback(List<IBaseVO> list)
		{
			super();
			this.list = list;
		}

		@Override
		public void onCallback(IBaseVO t)
		{
			this.list.add(t);
		}

	}

	@Autowired
	private XmlVOMapper voXmlMapper;

	@Autowired
	private IBaseEntityService baseEntityService;

	public void importVOs(InputStream inputStream)
	{
		try
		{
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);

			while (eventReader.hasNext())
			{
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement())
				{
					StartElement voStartElement = event.asStartElement();
					XmlElementDescriptor voStartElementDescriptor = this.voXmlMapper.getElementDescriptor(voStartElement.getName().getLocalPart());

					if (!voStartElementDescriptor.isList() && !voStartElementDescriptor.isReference())
					{
						final IBaseVO vo = ConstructorUtils.invokeConstructor(voStartElementDescriptor.getVoClass(), new Object[0]);

						StartElement voAttributeStartElement = null;
						IAttributeDescriptor<?> attributeDescriptor = null;

						// loop value object attributes
						while (eventReader.hasNext() && !isEndElementFor(event, voStartElement))
						{
							event = eventReader.nextEvent();

							if (attributeDescriptor != null)
							{
								if (isEndElementFor(event, voAttributeStartElement))
								{
									voAttributeStartElement = null;
									attributeDescriptor = null;
									continue;
								}

								if (IBaseVO.class.isAssignableFrom(attributeDescriptor.getAttributeType()))
								{
									resolveVOReference(eventReader, event, (Class<IBaseVO>) attributeDescriptor.getAttributeType(),
											new VOAttributeSetteCallback(vo, attributeDescriptor.getAttributeName()));
								}
								else if (List.class.isAssignableFrom(attributeDescriptor.getAttributeType()))
								{
									if (event.isStartElement())
									{
										StartElement listReferenceStartElement = event.asStartElement();

										XmlElementDescriptor listReferenceXmlElementDescriptor = this.voXmlMapper
												.getElementDescriptor(listReferenceStartElement.getName().getLocalPart());

										resolveVOReference(eventReader, event, (Class<IBaseVO>) attributeDescriptor.getListAttributeType(),
												new ListAdderCallback((List<IBaseVO>) vo.get(attributeDescriptor.getAttributeName())));
									}
								}
								else
								{
									Object convertedValue = ConvertUtils.convert(event.asCharacters().getData(), attributeDescriptor.getAttributeType());
									vo.set(attributeDescriptor.getAttributeName(), convertedValue);
									continue;
								}
							}

							if (attributeDescriptor == null && event.isStartElement())
							{
								voAttributeStartElement = event.asStartElement();
								attributeDescriptor = vo.getAttributeDescriptor(voAttributeStartElement.getName().getLocalPart());
								continue;
							}
						}

						this.baseEntityService.create(vo);
					}
				}
			}
		}
		catch (XMLStreamException e)
		{
			throw new RuntimeException(e);
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		catch (NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void resolveVOReference(XMLEventReader eventReader, XMLEvent currentEvent, Class<IBaseVO> voClass, SimpleCallback<IBaseVO> callback)
			throws XMLStreamException
	{
		if (currentEvent.isStartElement())
		{
			StartElement referenceStartElement = currentEvent.asStartElement();

			XmlElementDescriptor referenceXmlElementDescriptor = this.voXmlMapper.getElementDescriptor(referenceStartElement.getName().getLocalPart());

			if (referenceXmlElementDescriptor.isReference() && !referenceXmlElementDescriptor.isList())
			{
				Map<String, Object> referenceAttibutes = new HashMap<String, Object>();

				XMLEvent event = currentEvent;

				while (eventReader.hasNext() && !isEndElementFor(event, referenceStartElement))
				{
					event = eventReader.nextEvent();

					if (event.isStartElement())
					{
						StartElement referenceAttributeStartElement = event.asStartElement();
						IAttributeDescriptor<?> referenceAttributeDescriptor = BeanUtils.getAttributeDescriptor(voClass, referenceAttributeStartElement
								.getName().getLocalPart());

						event = eventReader.nextEvent();

						Object convertedValue = ConvertUtils.convert(event.asCharacters().getData(), referenceAttributeDescriptor.getAttributeType());
						referenceAttibutes.put(referenceAttributeStartElement.getName().getLocalPart(), convertedValue);
					}
				}

				IBaseVO referencedVO = getWithCriteriaMap(voClass, referenceAttibutes);
				callback.onCallback(referencedVO);

			}
		}
	}

	private boolean isEndElementFor(XMLEvent currentEvent, StartElement startElement)
	{
		return startElement != null && currentEvent.isEndElement()
				&& currentEvent.asEndElement().getName().getLocalPart().equals(startElement.getName().getLocalPart());

	}

	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

	public void setVoXmlMapper(XmlVOMapper voXmlMapper)
	{
		this.voXmlMapper = voXmlMapper;
	}

	public <T extends IBaseVO> T getWithCriteriaMap(Class<T> voClass, Map<String, Object> criteriaMap)
	{
		GenericFilterVO<T> genericFilterVO = ServerGenericFilterBuilder.createGenericFilter(voClass).getGenericFilter();

		for (Map.Entry<String, Object> criteriaEntry : criteriaMap.entrySet())
		{
			genericFilterVO.addCriteria(criteriaEntry.getKey(), criteriaEntry.getValue());
		}

		List<T> vos = this.baseEntityService.filter(genericFilterVO);

		if (vos.size() == 1)
		{
			return vos.get(0);
		}
		else
		{
			throw new RuntimeException(String.format("reference matches more than than one or no value object (criterias: %s", criteriaMap.toString()));
		}

	}

}