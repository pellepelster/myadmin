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

	protected BaseControlModel()
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
		return getFirstString(this.dictionaryControlVO.getColumnLabel(), this.dictionaryControlVO.getLabel(), this.dictionaryControlVO.getName());
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

	private boolean isEmpty(String string)
	{
		return string == null || string.trim().isEmpty();
	}

	private String getFirstString(String string1, String string2, String string3)
	{
		if (!isEmpty(string1))
		{
			return string1;
		}

		if (!isEmpty(string2))
		{
			return string2;
		}

		if (!isEmpty(string3))
		{
			return string3;
		}

		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getEditorLabel()
	{
		return getFirstString(this.dictionaryControlVO.getEditorLabel(), this.dictionaryControlVO.getLabel(), this.dictionaryControlVO.getName());
	}

	/** {@inheritDoc} */
	@Override
	public String getFilterLabel()
	{
		return getFirstString(this.dictionaryControlVO.getFilterLabel(), this.dictionaryControlVO.getLabel(), this.dictionaryControlVO.getName());
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

	/** {@inheritDoc} */
	@Override
	public boolean isMandatory()
	{
		return this.dictionaryControlVO.getMandatory() != null && this.dictionaryControlVO.getMandatory();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isReadonly()
	{
		return this.dictionaryControlVO.getReadOnly() != null && this.dictionaryControlVO.getReadOnly();
	}
}
