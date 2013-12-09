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

import com.google.common.base.Objects;

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
		this.voName = dictionaryVO.getEntityName();
		this.dictionaryVO = dictionaryVO;

		if (dictionaryVO.getSearch() != null)
		{
			this.searchModel = new SearchModel(this, dictionaryVO.getSearch(), this.voName);
		}

		if (dictionaryVO.getEditor() != null)
		{
			this.editorModel = new EditorModel(this, dictionaryVO.getEditor(), this.voName);
		}

		for (DictionaryControlVO controlVO : dictionaryVO.getLabelControls())
		{
			this.labelControls.add(ControlModelFactory.getControlModel(this, controlVO));
		}
	}

	/** {@inheritDoc} */
	@Override
	public IEditorModel getEditorModel()
	{
		return this.editorModel;
	}

	/** {@inheritDoc} */
	@Override
	public List<IBaseControlModel> getLabelControls()
	{
		return this.labelControls;
	}

	/** {@inheritDoc} */
	@Override
	public String getName()
	{
		return this.dictionaryVO.getName();
	}

	/** {@inheritDoc} */
	@Override
	public ISearchModel getSearchModel()
	{
		return this.searchModel;
	}

	/** {@inheritDoc} */
	@Override
	public String getVOName()
	{
		return this.voName;
	}

	@Override
	public String getLabel()
	{
		return Objects.firstNonNull(this.dictionaryVO.getLabel(), getName());
	}

	@Override
	public String getPluralLabel()
	{
		return Objects.firstNonNull(this.dictionaryVO.getPluralLabel(), getLabel());
	}

}
