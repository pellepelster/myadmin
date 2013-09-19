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

import javax.annotation.Resource;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.jpql.AssociationVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.DictionaryModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryContainerVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryEditorVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryFilterVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryResultVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionarySearchVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryVO;
import de.pellepelster.myadmin.client.web.services.IDictionaryServiceGWT;
import de.pellepelster.myadmin.db.IBaseVODAO;
import de.pellepelster.myadmin.server.core.query.ServerGenericFilterBuilder;

public class DictionaryServiceImpl implements IDictionaryServiceGWT {

	@Resource
	private IBaseVODAO baseVODAO;

	private void addContainerAssociations(AssociationVO parentAssociationVO, IAttributeDescriptor<?> attributeDescriptor) {
		AssociationVO containerAssociationVO = parentAssociationVO.addAssociation(attributeDescriptor);
		containerAssociationVO.addAssociation(DictionaryContainerVO.FIELD_CHILDREN);

		addControlAssociations(containerAssociationVO.addAssociation(DictionaryContainerVO.FIELD_CONTROLS));
	}

	private void addControlAssociations(AssociationVO controlAssociationVO) {
		controlAssociationVO.addAssociation(DictionaryControlVO.FIELD_DATATYPE);
		controlAssociationVO.addAssociation(DictionaryControlVO.FIELD_LABELCONTROLS);
	}

	/** {@inheritDoc} */
	@Override
	public List<IDictionaryModel> getDictionaries(List<String> dictionaryNames) {
		List<IDictionaryModel> result = new ArrayList<IDictionaryModel>();

		for (DictionaryVO dictionaryVO : getDictionariesInternal(dictionaryNames)) {
			result.add(new DictionaryModel(dictionaryVO));
		}

		return result;
	}

	public List<IDictionaryModel> getAllDictionaries() {

		List<IDictionaryModel> result = new ArrayList<IDictionaryModel>();

		for (DictionaryVO tmpDictionaryVO : baseVODAO.getAll(DictionaryVO.class)) {
			DictionaryVO dictionaryVO = getDictionaryInternal(tmpDictionaryVO.getName());
			result.add(new DictionaryModel(dictionaryVO));
		}

		return result;
	}

	private List<DictionaryVO> getDictionariesInternal(List<String> dictionaryNames) {
		List<DictionaryVO> result = new ArrayList<DictionaryVO>();

		for (String dictionaryName : dictionaryNames) {
			DictionaryVO dictionaryVO = getDictionaryInternal(dictionaryName);

			if (dictionaryVO != null) {
				result.add(getDictionaryInternal(dictionaryName));
			}
		}

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public IDictionaryModel getDictionary(String dictionaryName) {
		DictionaryVO dictionaryVO = getDictionaryInternal(dictionaryName);
		return new DictionaryModel(dictionaryVO);
	}

	private DictionaryVO getDictionaryInternal(String dictionaryName) {

		GenericFilterVO<DictionaryVO> genericFilterVO = ServerGenericFilterBuilder.createGenericFilter(DictionaryVO.class).addCriteria(DictionaryVO.FIELD_NAME, dictionaryName).getGenericFilter();

		addControlAssociations(genericFilterVO.addAssociation(DictionaryVO.FIELD_CONTROLAGGREGATES));
		addControlAssociations(genericFilterVO.addAssociation(DictionaryVO.FIELD_LABELCONTROLS));

		AssociationVO dictionaryEditor = genericFilterVO.addAssociation(DictionaryVO.FIELD_EDITOR);
		addContainerAssociations(dictionaryEditor, DictionaryEditorVO.FIELD_CONTAINER);

		AssociationVO dictionarySearch = genericFilterVO.addAssociation(DictionaryVO.FIELD_SEARCH);
		addControlAssociations(dictionarySearch.addAssociation(DictionarySearchVO.FIELD_RESULT).addAssociation(DictionaryResultVO.FIELD_CONTROLS));

		AssociationVO dictionaryFilter = dictionarySearch.addAssociation(DictionarySearchVO.FIELD_FILTER);
		addContainerAssociations(dictionaryFilter, DictionaryFilterVO.FIELD_CONTAINER);

		List<DictionaryVO> result = this.baseVODAO.filter(genericFilterVO);

		if (result.size() == 1) {
			return result.get(0);
		} else {
			throw new RuntimeException(String.format("dictionary '%s' not found", dictionaryName));
		}
	}

	public void setBaseVODAO(IBaseVODAO baseVODAO) {
		this.baseVODAO = baseVODAO;
	}

}
