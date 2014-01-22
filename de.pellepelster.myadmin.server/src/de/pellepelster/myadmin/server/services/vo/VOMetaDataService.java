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
package de.pellepelster.myadmin.server.services.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.db.util.BeanUtils;
import de.pellepelster.myadmin.db.util.IEntityVOMapper;
import de.pellepelster.myadmin.server.services.DirectedGraph;

public class VOMetaDataService implements InitializingBean
{
	private final static Logger LOG = Logger.getLogger(VOMetaDataService.class);

	private List<Class<? extends IBaseVO>> voClasses = new ArrayList<Class<? extends IBaseVO>>();

	private final Map<Class<? extends IBaseVO>, Class<?>> voXmlMappings = new HashMap<Class<? extends IBaseVO>, Class<?>>();

	private final Map<Class<? extends IBaseVO>, Class<?>> voXmlListWrapperMappings = new HashMap<Class<? extends IBaseVO>, Class<?>>();

	private final Map<Class<? extends IBaseVO>, Class<?>> voXmlReferenceListWrapperMappings = new HashMap<Class<? extends IBaseVO>, Class<?>>();

	private final Map<Class<? extends IBaseVO>, Class<?>> voXmlReferenceMappings = new HashMap<Class<? extends IBaseVO>, Class<?>>();

	private final Map<String, Class<?>> xmlRootElementClasses = new HashMap<String, Class<?>>();

	@Override
	public void afterPropertiesSet() throws Exception
	{
		LOG.info(String.format("%d vo classes found", this.voClasses.size()));

		for (Class<? extends IBaseVO> voClass : this.voClasses)
		{
			LOG.info(String.format("%s", voClass.getName()));
		}

		DirectedGraph<Class<? extends IBaseVO>> directedGraph = new DirectedGraph<Class<? extends IBaseVO>>();

		for (Class<? extends IBaseVO> voClass : this.voClasses)
		{
			directedGraph.addNode(voClass);
		}

		for (Class<? extends IBaseVO> voClass : this.voClasses)
		{
			Set<Class<? extends IBaseVO>> dependentClasses = BeanUtils.getReferencedVOs(voClass);

			for (Class<? extends IBaseVO> dependentClass : dependentClasses)
			{
				if (!voClass.equals(dependentClass))
				{
					directedGraph.addEdge(voClass, dependentClass);
				}
			}
		}

		Collections.sort(this.voClasses, new Comparator<Class<? extends IBaseVO>>()
		{

			@Override
			public int compare(Class<? extends IBaseVO> voClass1, Class<? extends IBaseVO> voClass2)
			{
				Set<Class<? extends IBaseVO>> referencedVO1s = BeanUtils.getReferencedVOs(voClass1);
				Set<Class<? extends IBaseVO>> referencedVO2s = BeanUtils.getReferencedVOs(voClass2);

				if (referencedVO1s.contains(voClass2) && referencedVO2s.contains(referencedVO1s))
				{
					return 0;
				}
				else
				{
					if (referencedVO1s.contains(referencedVO2s))
					{
						return -1;
					}
					else
					{
						return 1;

					}
				}

			}
		});
		Collections.reverse(this.voClasses);
	}

	public Class<?> getXmlClassForVOClass(Class<? extends IBaseVO> voClass)
	{
		return this.voXmlMappings.get(voClass);
	}

	public Class<?> getXmlListWrapperClassForVOClass(Class<? extends IBaseVO> voClass)
	{
		if (this.voXmlListWrapperMappings.containsKey(voClass))
		{
			return this.voXmlListWrapperMappings.get(voClass);
		}
		else
		{
			throw new RuntimeException(String.format("no xml list wrapper found for '%s'", voClass.getName()));
		}
	}

	public Class<?> getXmlReferenceClassForVOClass(Class<? extends IBaseVO> voClass)
	{
		return this.voXmlReferenceMappings.get(voClass);
	}

	public Class<?> getXmlReferenceListWrapperClassForVOClass(Class<? extends IBaseVO> voClass)
	{
		return this.voXmlReferenceListWrapperMappings.get(voClass);
	}

	public Class<?> getXmlRootClassByElementName(String elementName)
	{
		return this.xmlRootElementClasses.get(elementName);
	}

	public Collection<Class<?>> getXmlRootClasses()
	{
		return this.xmlRootElementClasses.values();
	}

	public List<Class<? extends IBaseVO>> getVOClasses()
	{
		return this.voClasses;
	}

	@Autowired
	public void setEntityVOMappers(List<IEntityVOMapper> entityVOMappers)
	{
		for (IEntityVOMapper entityVOMapper : entityVOMappers)
		{
			this.voClasses.addAll(entityVOMapper.getVOClasses());
		}
	}

}
