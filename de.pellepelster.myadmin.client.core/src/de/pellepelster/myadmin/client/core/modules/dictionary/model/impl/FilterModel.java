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

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IFilterModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.containers.CompositeContainerModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryFilterVO;

/**
 * Database based implementation of {@link IFilterModel}
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class FilterModel extends BaseModel implements IFilterModel
{
	private static final long serialVersionUID = -8926959374648271929L;
	
	private ICompositeModel composite;
	private String voName;
	private String name;

	public FilterModel()
	{
		super(null);
	}

	/**
	 * Constructor for {@link FilterModel}
	 * 
	 * @param parent
	 * @param dictionaryFilterVO
	 * @param voName
	 */
	public FilterModel(IBaseModel parent, DictionaryFilterVO dictionaryFilterVO, String voName)
	{
		super(parent);
		this.voName = voName;
		name = dictionaryFilterVO.getName();

		if (dictionaryFilterVO.getContainer() != null)
		{
			composite = new CompositeContainerModel(parent, dictionaryFilterVO.getContainer());
		}
	}

	/** {@inheritDoc} */
	@Override
	public ICompositeModel getCompositeModel()
	{
		return composite;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	/* * {@inheritDoc} */
	public String getVOName()
	{
		return voName;
	}
}
