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
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IEditorModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.containers.CompositeContainerModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryEditorVO;

/**
 * Database based implementation of {@link IEditorModel}
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public class EditorModel extends BaseModel implements IEditorModel
{

	private static final long serialVersionUID = -2746470946400505577L;

	private ICompositeModel composite;
	private String voName;
	private DictionaryEditorVO dictionaryEditorVO;

	public EditorModel()
	{
		super(null);
	}

	/**
	 * Constructor for {@link EditorModel}
	 * 
	 * @param parent
	 * @param dictionaryEditorVO
	 * @param voName
	 */
	public EditorModel(IBaseModel parent, DictionaryEditorVO dictionaryEditorVO, String voName)
	{
		super(parent);
		this.voName = voName;
		this.dictionaryEditorVO = dictionaryEditorVO;

		if (dictionaryEditorVO.getContainer() != null)
		{
			this.composite = new CompositeContainerModel(this, dictionaryEditorVO.getContainer());
		}
	}

	/** {@inheritDoc} */
	@Override
	public ICompositeModel getCompositeModel()
	{
		return this.composite;
	}

	/** {@inheritDoc} */
	@Override
	public String getName()
	{
		return this.dictionaryEditorVO.getName();
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle()
	{
		if (this.dictionaryEditorVO.getTitle() != null && !this.dictionaryEditorVO.getTitle().isEmpty())
		{
			return this.dictionaryEditorVO.getTitle();
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
		return this.voName;
	}
}
