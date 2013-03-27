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

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;

/**
 * Implementation of {@link IReferenceControlModel}
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class ReferenceControlModel extends BaseControlModel implements IReferenceControlModel
{

	private static final long serialVersionUID = -1129427988759796790L;
	
	private String dictionaryName;
	private List<IBaseControlModel> labelControls = new ArrayList<IBaseControlModel>();

	public ReferenceControlModel()
	{
	}

	public ReferenceControlModel(IBaseModel parent, DictionaryControlVO dictionaryControlVO)
	{
		super(parent, dictionaryControlVO);

		dictionaryName = dictionaryControlVO.getDictionary();
		labelControls = ControlModelFactory.getControlModel(parent, dictionaryControlVO.getLabelControls());
	}

	/** {@inheritDoc} */
	@Override
	public String getDictionaryName()
	{
		return dictionaryName;
	}

	/** {@inheritDoc} */
	@Override
	public List<IBaseControlModel> getLabelControls()
	{
		return labelControls;
	}

}
