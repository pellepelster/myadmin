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

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.services.IDictionaryService;
import de.pellepelster.myadmin.db.IBaseVODAO;
import de.pellepelster.myadmin.db.daos.IVODAOCallback;
import de.pellepelster.myadmin.db.index.ISearchIndexService;
import de.pellepelster.myadmin.server.entities.dictionary.Dictionary;
import de.pellepelster.myadmin.server.services.events.DictionaryEvent;
import de.pellepelster.myadmin.server.services.search.DictionaryIndexElement;

public class DictionaryMetaDataService implements InitializingBean, ApplicationListener<DictionaryEvent> {

	private final static Logger LOG = Logger.getLogger(DictionaryMetaDataService.class);

	private Map<String, Map<String, List<String>>> vosToIndex;

	@Autowired
	private IDictionaryService dictionaryService;

	@Autowired
	private ISearchIndexService searchIndexService;

	@Autowired
	private IBaseVODAO baseVODAO;

	@Override
	public void afterPropertiesSet() throws Exception {

		this.baseVODAO.getVODAOCallbacks().add(new IVODAOCallback() {
			@Override
			public <VOType extends IBaseVO> void onAdd(VOType vo) {
				if (vo.getClass().getName().startsWith(Dictionary.class.getName())) {
					DictionaryMetaDataService.this.vosToIndex = null;
				} else {
					if (doIndexVO(vo)) {
						for (Map.Entry<String, List<String>> i : DictionaryMetaDataService.this.getVOsToIndex().get(vo.getClass().getName()).entrySet()) {
							DictionaryMetaDataService.this.searchIndexService.add(new DictionaryIndexElement(i.getKey(), i.getValue(), vo));
						}
					}
				}
			}
		});
	}

	private boolean doIndexVO(IBaseVO vo) {
		return DictionaryMetaDataService.this.searchIndexService != null && isIndexVO(vo);
	}

	private boolean isIndexVO(IBaseVO vo) {
		return vo != null && this.getVOsToIndex().containsKey(vo.getClass().getName()) && !this.getVOsToIndex().get(vo.getClass().getName()).isEmpty();
	}

	public void setDictionaryService(IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public void setSearchIndexService(ISearchIndexService searchIndexService) {
		this.searchIndexService = searchIndexService;
	}

	public Map<String, Map<String, List<String>>> getVOsToIndex() {

		if (this.vosToIndex == null) {

			this.vosToIndex = new HashMap<String, Map<String, List<String>>>();

			LOG.info(String.format("searching vo attributes to index"));

			for (IDictionaryModel dictionaryModel : this.dictionaryService.getAllDictionaries()) {
				List<String> indexAttributes = new ArrayList<String>();

				for (IBaseControlModel baseControlModel : dictionaryModel.getLabelControls()) {
					indexAttributes.add(baseControlModel.getAttributePath());
				}

				if (!indexAttributes.isEmpty()) {
					if (!this.vosToIndex.containsKey(dictionaryModel.getVOName())) {
						Map<String, List<String>> map = new HashMap<String, List<String>>();
						this.vosToIndex.put(dictionaryModel.getVOName(), map);
					}

					LOG.info(String.format("the following attributes for vo '%s' will be indexed: %s", dictionaryModel.getVOName(), indexAttributes.toString()));
					this.vosToIndex.get(dictionaryModel.getVOName()).put(dictionaryModel.getName(), indexAttributes);
				}
			}

		}

		return this.vosToIndex;
	}

	@Override
	public void onApplicationEvent(DictionaryEvent event) {
		vosToIndex = null;
	}
}
