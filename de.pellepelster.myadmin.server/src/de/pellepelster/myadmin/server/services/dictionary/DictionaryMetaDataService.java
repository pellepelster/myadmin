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
package de.pellepelster.myadmin.server.services.dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelProvider;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.db.daos.BaseVODAO;
import de.pellepelster.myadmin.db.daos.IVODAOCallback;
import de.pellepelster.myadmin.db.index.ISearchService;
import de.pellepelster.myadmin.server.entities.dictionary.Dictionary;
import de.pellepelster.myadmin.server.services.search.DictionarySearchElementFactory;

public class DictionaryMetaDataService implements InitializingBean
{

	private Map<String, List<IDictionaryModel>> vosToDictionaryModels;

	private Optional<ISearchService> searchService = Optional.absent();

	@Autowired
	private BaseVODAO baseVODAO;

	public DictionaryMetaDataService()
	{
		super();
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		if (this.searchService.isPresent())
		{
			this.baseVODAO.getVODAOCallbacks().add(new IVODAOCallback()
			{
				@Override
				public <VOType extends IBaseVO> void onAdd(VOType vo)
				{
					for (IDictionaryModel dictionaryModel : DictionaryMetaDataService.this.getModelsForVO(vo))
					{
						DictionaryMetaDataService.this.searchService.get().add(DictionarySearchElementFactory.createElement(dictionaryModel, vo));
					}
				}

				@Override
				public void onDeleteAll(Class<? extends IBaseVO> voClass)
				{
					for (IDictionaryModel dictionaryModel : DictionaryMetaDataService.this.getModelsForVO(voClass))
					{
						DictionaryMetaDataService.this.searchService.get().deleteAll(null);
					}
				}

				@Override
				public <T extends IBaseVO> void onDelete(T vo)
				{
					for (IDictionaryModel dictionaryModel : DictionaryMetaDataService.this.getModelsForVO(vo))
					{
						DictionaryMetaDataService.this.searchService.get().delete(null);
					}
				}

				@Override
				public <VOType extends IBaseVO> void onUpdate(VOType vo)
				{
					for (IDictionaryModel dictionaryModel : DictionaryMetaDataService.this.getModelsForVO(vo))
					{
						DictionaryMetaDataService.this.searchService.get().update(DictionarySearchElementFactory.createElement(dictionaryModel, vo));
					}
				}
			});
		}
	}

	@Autowired(required = false)
	public void setSearchService(ISearchService searchService)
	{
		this.searchService = Optional.of(searchService);
	}

	public List<IDictionaryModel> getModelsForVO(IBaseVO vo)
	{
		return getModelsForVO(vo.getClass());
	}

	public List<IDictionaryModel> getModelsForVO(Class<? extends IBaseVO> voClass)
	{
		return getModelsForVO(voClass.getName());
	}

	@SuppressWarnings("unchecked")
	public List<IDictionaryModel> getModelsForVO(String voClassName)
	{

		if (this.vosToDictionaryModels == null)
		{
			this.vosToDictionaryModels = new HashMap<String, List<IDictionaryModel>>();

			for (IDictionaryModel dictionaryModel : DictionaryModelProvider.getAllDictionaries())
			{
				Class<?> tmpVoClass = null;
				try
				{
					tmpVoClass = Class.forName(dictionaryModel.getVoName());
				}
				catch (Exception e)
				{
					throw new RuntimeException(e);
				}

				if (!this.vosToDictionaryModels.containsKey(dictionaryModel.getVoName()))
				{
					this.vosToDictionaryModels.put(tmpVoClass.getName(), new ArrayList<IDictionaryModel>());
				}

				this.vosToDictionaryModels.get(tmpVoClass.getName()).add(dictionaryModel);
			}

		}

		if (DictionaryMetaDataService.this.searchService != null && !voClassName.startsWith(Dictionary.class.getName())
				&& this.vosToDictionaryModels.containsKey(voClassName))
		{
			return this.vosToDictionaryModels.get(voClassName);
		}
		else
		{
			return Collections.EMPTY_LIST;
		}

	}

}
