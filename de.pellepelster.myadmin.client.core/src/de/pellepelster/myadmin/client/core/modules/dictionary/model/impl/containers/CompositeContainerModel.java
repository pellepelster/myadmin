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
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.BaseModel;
import de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.controls.ControlModelFactory;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryContainerVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;

/**
 * Database based implementation of {@link ICompositeModel}
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class CompositeContainerModel extends BaseModel implements ICompositeModel
{

	private static final long serialVersionUID = 4489840828161395246L;
	
	private List<IBaseContainerModel> children = new ArrayList<IBaseContainerModel>();
	private List<IBaseControlModel> controls = new ArrayList<IBaseControlModel>();
	private DictionaryContainerVO containerVO;

	public CompositeContainerModel()
	{
		super(null);
	}

	/**
	 * Constructor for {@link CompositeContainerModel}
	 * 
	 * @param parent
	 * @param containerVO
	 */
	public CompositeContainerModel(IBaseModel parent, DictionaryContainerVO containerVO)
	{
		super(parent);
		this.containerVO = containerVO;

		for (DictionaryContainerVO lCompositeVO : containerVO.getChildren())
		{
			children.add(ContainerModelFactory.getContainerModel(this, lCompositeVO));
		}

		for (DictionaryControlVO controlVO : containerVO.getControls())
		{
			controls.add(ControlModelFactory.getControlModel(this, controlVO));
		}
	}

	/** {@inheritDoc} */
	@Override
	public List<IBaseContainerModel> getChildren()
	{
		return children;
	}

	/** {@inheritDoc} */
	@Override
	public List<IBaseControlModel> getControls()
	{
		return controls;
	}

	/** {@inheritDoc} */
	@Override
	public String getName()
	{
		return containerVO.getName();
	}

}
