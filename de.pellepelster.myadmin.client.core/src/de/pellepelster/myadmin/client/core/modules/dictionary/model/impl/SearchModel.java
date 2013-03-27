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

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IFilterModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IResultModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.ISearchModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryFilterVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionarySearchVO;

/**
 * Database based implementation of {@link ISearchModel}
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class SearchModel extends BaseModel implements ISearchModel
{

	private static final long serialVersionUID = -5816734592260334814L;
	private List<IFilterModel> filter = new ArrayList<IFilterModel>();
	private IResultModel result;
	private DictionarySearchVO dictionarySearchVO;
	private String voName;

	private SearchModel()
	{
		super(null);
	}

	/**
	 * Constructor for {@link SearchModel}
	 * 
	 * @param parent
	 * @param dictionarySearchVO
	 * @param voName
	 */
	public SearchModel(IBaseModel parent, DictionarySearchVO dictionarySearchVO, String voName)
	{
		super(parent);

		this.dictionarySearchVO = dictionarySearchVO;
		this.voName = voName;

		for (DictionaryFilterVO filterVO : dictionarySearchVO.getFilter())
		{
			filter.add(new FilterModel(this, filterVO, voName));
		}

		result = new ResultModel(this, dictionarySearchVO.getResult());

	}

	/** {@inheritDoc} */
	@Override
	public List<IFilterModel> getFilterModel()
	{
		return filter;
	}

	/** {@inheritDoc} */
	@Override
	public String getName()
	{
		return dictionarySearchVO.getName();
	}

	/** {@inheritDoc} */
	@Override
	public IResultModel getResultModel()
	{
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle()
	{
		if (dictionarySearchVO.getTitle() != null && !dictionarySearchVO.getTitle().isEmpty())
		{
			return dictionarySearchVO.getTitle();
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
