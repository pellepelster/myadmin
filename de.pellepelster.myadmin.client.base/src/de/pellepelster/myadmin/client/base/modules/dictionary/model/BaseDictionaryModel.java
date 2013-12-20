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
package de.pellepelster.myadmin.client.base.modules.dictionary.model;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;

public abstract class BaseDictionaryModel extends BaseModel<Object> implements IDictionaryModel
{

	private static final long serialVersionUID = 1732059266448744364L;

	private List<IBaseControlModel> labelControls = new ArrayList<IBaseControlModel>();

	public BaseDictionaryModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public IEditorModel getEditorModel()
	{
		return null;
	}

	@Override
	public List<IBaseControlModel> getLabelControls()
	{
		return this.labelControls;
	}

	@Override
	public ISearchModel getSearchModel()
	{
		return null;
	}

	@Override
	public String getLabel()
	{
		return getName();
	}

	@Override
	public String getPluralLabel()
	{
		return getName();
	}

	@Override
	public String getVOName()
	{
		throw new RuntimeException("no vo name set");
	}

}
