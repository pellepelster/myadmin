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
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IResultModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.controls.ControlModelFactory;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryResultVO;

/**
 * Database based implementation of {@link IResultModel}
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public class ResultModel extends BaseModel implements IResultModel
{
	private static final long serialVersionUID = 5301525147488261695L;
	
	private List<IBaseControlModel> controls = new ArrayList<IBaseControlModel>();
	private DictionaryResultVO resultVO;
	private String name;

	private ResultModel()
	{
		super(null);
	}

	/**
	 * Constructor for {@link ResultModel}
	 * 
	 * @param dictionaryObjectVO
	 * @param voClass
	 */
	public ResultModel(IBaseModel parent, DictionaryResultVO resultVO)
	{
		super(parent);
		this.resultVO = resultVO;
		name = resultVO.getName();

		for (DictionaryControlVO controlVO : resultVO.getControls())
		{
			controls.add(ControlModelFactory.getControlModel(parent, controlVO));
		}
	}

	/** {@inheritDoc} */
	@Override
	public List<IBaseControlModel> getControls()
	{
		return controls;
	}

	/** {@inheritDoc} */
	@Override
	public int getMaxResults()
	{
		return 1000;
	}

	@Override
	public String getName()
	{
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public int getPagingSize()
	{
		if (resultVO.getPagingSize() > 0)
		{
			return resultVO.getPagingSize();
		}
		else
		{
			return IResultModel.DEFAULT_PAGING_SIZE;
		}
	}

}
