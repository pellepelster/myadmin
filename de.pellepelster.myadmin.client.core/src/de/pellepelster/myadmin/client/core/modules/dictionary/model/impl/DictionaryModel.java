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
package de.pellepelster.myadmin.client.core.modules.dictionary.model.impl;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IEditorModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.ISearchModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.controls.ControlModelFactory;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryVO;

/**
 * Database based implementation of {@link IDictionaryModel}
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class DictionaryModel extends BaseModel implements IDictionaryModel
{

	private static final long serialVersionUID = 1501950602102650862L;

	private IEditorModel editorModel;
	private List<IBaseControlModel> labelControls = new ArrayList<IBaseControlModel>();
	private DictionaryVO dictionaryVO;
	private ISearchModel searchModel;
	private String voName;

	public DictionaryModel()
	{
		super(null);
	}

	/**
	 * Constructor for {@link DictionaryModel}
	 * 
	 * @param dictionaryVO
	 */
	public DictionaryModel(DictionaryVO dictionaryVO)
	{
		super(null);
		voName = dictionaryVO.getEntityName();
		this.dictionaryVO = dictionaryVO;

		if (dictionaryVO.getSearch() != null)
		{
			searchModel = new SearchModel(this, dictionaryVO.getSearch(), voName);
		}
		editorModel = new EditorModel(this, dictionaryVO.getEditor(), voName);

		for (DictionaryControlVO controlVO : dictionaryVO.getLabelControls())
		{
			labelControls.add(ControlModelFactory.getControlModel(this, controlVO));
		}
	}

	/** {@inheritDoc} */
	@Override
	public IEditorModel getEditorModel()
	{
		return editorModel;
	}

	/** {@inheritDoc} */
	@Override
	public List<IBaseControlModel> getLabelControls()
	{
		return labelControls;
	}

	/** {@inheritDoc} */
	@Override
	public String getName()
	{
		return dictionaryVO.getName();
	}

	/** {@inheritDoc} */
	@Override
	public ISearchModel getSearchModel()
	{
		return searchModel;
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle()
	{
		if (dictionaryVO.getTitle() != null && !dictionaryVO.getTitle().isEmpty())
		{
			return dictionaryVO.getTitle();
		}
		else
		{
			return getName();
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getVOName()
	{
		return voName;
	}

}
