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
package de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.controls;

import de.pellepelster.myadmin.client.base.jpql.RelationalOperator;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.BaseModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryDatatypeVO;

/**
 * Base control model based implementation
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public abstract class BaseControlModel extends BaseModel implements IBaseControlModel
{
	private static final long serialVersionUID = 7370505030378400151L;

	private DictionaryControlVO dictionaryControlVO;
	private Integer width;

	public BaseControlModel()
	{
		super(null);
	}

	/**
	 * Constructor for {@link BaseControlModel}
	 * 
	 * @param parent
	 * @param dictionaryControlVO
	 */
	public BaseControlModel(IBaseModel parent, DictionaryControlVO dictionaryControlVO)
	{
		super(parent);
		this.dictionaryControlVO = dictionaryControlVO;
		this.width = dictionaryControlVO.getWidth();
	}

	/** {@inheritDoc} */
	@Override
	public String getAttributePath()
	{
		return this.dictionaryControlVO.getAttributePath();
	}

	/** {@inheritDoc} */
	@Override
	public String getColumnLabel()
	{
		if (isEmpty(this.dictionaryControlVO.getColumnLabel()))
		{
			return this.dictionaryControlVO.getLabel();
		}
		else
		{
			return this.dictionaryControlVO.getColumnLabel();
		}
	}

	protected DictionaryControlVO getControlVO()
	{
		return this.dictionaryControlVO;
	}

	protected DictionaryDatatypeVO getDatatypeVO()
	{
		return this.dictionaryControlVO.getDatatype();
	}

	protected DictionaryControlVO getDictionaryControlVO()
	{
		return this.dictionaryControlVO;
	}

	/** {@inheritDoc} */
	@Override
	public String getEditorLabel()
	{
		if (isEmpty(this.dictionaryControlVO.getEditorLabel()))
		{
			return this.dictionaryControlVO.getLabel();
		}
		else
		{
			return this.dictionaryControlVO.getEditorLabel();
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getFilterLabel()
	{
		if (isEmpty(this.dictionaryControlVO.getFilterLabel()))
		{
			return this.dictionaryControlVO.getLabel();
		}
		else
		{
			return this.dictionaryControlVO.getFilterLabel();
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getName()
	{
		return this.dictionaryControlVO.getName();
	}

	/** {@inheritDoc} */
	@Override
	public RelationalOperator[] getRelationalOperators()
	{
		return new RelationalOperator[] { RelationalOperator.EQUAL };
	}

	/** {@inheritDoc} */
	@Override
	public String getToolTip()
	{
		if (isEmpty(this.dictionaryControlVO.getToolTip()))
		{
			return this.dictionaryControlVO.getLabel();
		}
		else
		{
			return this.dictionaryControlVO.getToolTip();
		}
	}

	public Integer getWidthHintInternal()
	{
		return this.width;
	}

	private boolean isEmpty(String string)
	{
		return string == null || string.trim().isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMandatory()
	{
		return this.dictionaryControlVO.getMandatory();
	}

}
