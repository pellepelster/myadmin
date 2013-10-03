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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.web.services.IDictionaryService;
import de.pellepelster.myadmin.db.IBaseVODAO;
import de.pellepelster.myadmin.db.daos.IVODAOCallback;
import de.pellepelster.myadmin.db.index.ISearchIndexService;
import de.pellepelster.myadmin.server.entities.dictionary.Dictionary;
import de.pellepelster.myadmin.server.services.events.DictionaryEvent;
import de.pellepelster.myadmin.server.services.search.DictionarySearchIndexFactory;

@Component
public class DictionaryMetaDataService implements InitializingBean, ApplicationListener<DictionaryEvent>
{

	private final static Logger LOG = Logger.getLogger(DictionaryMetaDataService.class);

	private Map<String, List<IDictionaryModel>> vosToDictionaryModels;

	@Autowired
	private IDictionaryService dictionaryService;

	@Autowired
	private ISearchIndexService searchIndexService;

	@Autowired
	private IBaseVODAO baseVODAO;

	@Override
	public void afterPropertiesSet() throws Exception
	{

		this.baseVODAO.getVODAOCallbacks().add(new IVODAOCallback()
		{
			@Override
			public <VOType extends IBaseVO> void onAdd(VOType vo)
			{
				if (doIndexVO(vo))
				{
					for (IDictionaryModel dictionaryModel : DictionaryMetaDataService.this.getVOsToIndex().get(vo.getClass().getName()))
					{
						DictionaryMetaDataService.this.searchIndexService.add(DictionarySearchIndexFactory.createElement(dictionaryModel, vo));
					}
				}
			}
		});
	}

	private boolean doIndexVO(IBaseVO vo)
	{
		return DictionaryMetaDataService.this.searchIndexService != null && isIndexVO(vo);
	}

	private boolean isIndexVO(IBaseVO vo)
	{
		return vo != null && !vo.getClass().getName().startsWith(Dictionary.class.getName()) && this.getVOsToIndex().containsKey(vo.getClass().getName())
				&& !this.getVOsToIndex().get(vo.getClass().getName()).isEmpty();
	}

	public void setDictionaryService(IDictionaryService dictionaryService)
	{
		this.dictionaryService = dictionaryService;
	}

	public void setSearchIndexService(ISearchIndexService searchIndexService)
	{
		this.searchIndexService = searchIndexService;
	}

	public Map<String, List<IDictionaryModel>> getVOsToIndex()
	{

		if (this.vosToDictionaryModels == null)
		{

			this.vosToDictionaryModels = new HashMap<String, List<IDictionaryModel>>();

			for (IDictionaryModel dictionaryModel : this.dictionaryService.getAllDictionaries())
			{
				if (!this.vosToDictionaryModels.containsKey(dictionaryModel.getVOName()))
				{
					this.vosToDictionaryModels.put(dictionaryModel.getVOName(), new ArrayList<IDictionaryModel>());
				}

				this.vosToDictionaryModels.get(dictionaryModel.getVOName()).add(dictionaryModel);
			}

		}

		return this.vosToDictionaryModels;
	}

	@Override
	public void onApplicationEvent(DictionaryEvent event)
	{
		switch (event.getDictionaryEventType())
		{
			case IMPORT_FINISHED:
				this.vosToDictionaryModels = null;
				break;
			default:
				break;
		}
	}
}
