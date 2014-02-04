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

public class DictionarySearchService implements InitializingBean
{

	private Map<Class<? extends IBaseVO>, List<IDictionaryModel>> vosToDictionaryModels;

	@Autowired(required = false)
	private Optional<ISearchService> searchIndexService = Optional.absent();

	@Autowired
	private BaseVODAO baseVODAO;

	@Override
	public void afterPropertiesSet() throws Exception
	{

		if (this.searchIndexService.isPresent())
		{
			this.baseVODAO.getVODAOCallbacks().add(new IVODAOCallback()
			{
				@Override
				public <VOType extends IBaseVO> void onAdd(VOType vo)
				{
					for (IDictionaryModel dictionaryModel : DictionarySearchService.this.getModelsForVO(vo))
					{
						DictionarySearchService.this.searchIndexService.get().add(DictionarySearchElementFactory.createElement(dictionaryModel, vo));
					}
				}

				@Override
				public void onDeleteAll(Class<? extends IBaseVO> voClass)
				{
					for (IDictionaryModel dictionaryModel : DictionarySearchService.this.getModelsForVO(voClass))
					{
						DictionarySearchService.this.searchIndexService.get().deleteAll(null);
					}
				}

				@Override
				public <T extends IBaseVO> void onDelete(T vo)
				{
					for (IDictionaryModel dictionaryModel : DictionarySearchService.this.getModelsForVO(vo))
					{
						DictionarySearchService.this.searchIndexService.get().delete(null);
					}
				}

				@Override
				public <VOType extends IBaseVO> void onUpdate(VOType vo)
				{
					// TODO Auto-generated method stub

				}
			});
		}
	}

	public void setSearchIndexService(ISearchService searchIndexService)
	{
		this.searchIndexService = Optional.of(searchIndexService);
	}

	public List<IDictionaryModel> getModelsForVO(IBaseVO vo)
	{
		return getModelsForVO(vo.getClass());
	}

	@SuppressWarnings("unchecked")
	public List<IDictionaryModel> getModelsForVO(Class<? extends IBaseVO> voClass)
	{

		if (this.vosToDictionaryModels == null)
		{
			this.vosToDictionaryModels = new HashMap<Class<? extends IBaseVO>, List<IDictionaryModel>>();

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
					this.vosToDictionaryModels.put((Class<? extends IBaseVO>) tmpVoClass, new ArrayList<IDictionaryModel>());
				}

				this.vosToDictionaryModels.get(tmpVoClass).add(dictionaryModel);
			}

		}

		if (DictionarySearchService.this.searchIndexService != null && !voClass.getName().startsWith(Dictionary.class.getName())
				&& this.vosToDictionaryModels.containsKey(voClass))
		{
			return this.vosToDictionaryModels.get(voClass);
		}
		else
		{
			return Collections.EMPTY_LIST;
		}

	}

}
