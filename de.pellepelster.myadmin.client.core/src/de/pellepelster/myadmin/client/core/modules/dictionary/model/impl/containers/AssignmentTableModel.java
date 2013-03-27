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
package de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.containers;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.BaseModel;
import de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.controls.ControlModelFactory;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryContainerVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;

/**
 * Grid control model
 * 
 * @author pelle
 * 
 */
public class AssignmentTableModel extends BaseModel implements IAssignmentTableModel
{

	private static final long serialVersionUID = 3553140747972753090L;
	private List<IBaseControlModel> columns = new ArrayList<IBaseControlModel>();
	private DictionaryContainerVO containerVO;

	private AssignmentTableModel()
	{
		super(null);
	}

	public AssignmentTableModel(IBaseModel parent, DictionaryContainerVO containerVO)
	{
		super(parent);
		this.containerVO = containerVO;

		for (DictionaryControlVO controlVO : containerVO.getControls())
		{
			columns.add(ControlModelFactory.getControlModel(parent, controlVO));
		}

	}

	/** {@inheritDoc} */
	@Override
	public String getAttributePath()
	{
		return containerVO.getAttributePath();
	}

	/** {@inheritDoc} */
	@Override
	public List<IBaseContainerModel> getChildren()
	{
		return new ArrayList<IBaseContainerModel>();
	}

	/** {@inheritDoc} */
	@Override
	public List<IBaseControlModel> getControls()
	{
		return columns;
	}

	/** {@inheritDoc} */
	@Override
	public String getDictionaryName()
	{
		return containerVO.getDictionaryName();
	}

	/** {@inheritDoc} */
	@Override
	public String getName()
	{
		return containerVO.getName();
	}

	/** {@inheritDoc} */
	@Override
	public Integer getVisibleRows()
	{
		return DEFAULT_VISBLE_ROWS;
	}

}
