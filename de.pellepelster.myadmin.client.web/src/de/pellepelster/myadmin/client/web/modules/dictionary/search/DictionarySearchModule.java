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
package de.pellepelster.myadmin.client.web.modules.dictionary.search;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelProvider;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.BaseDictionarySearchModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryElementUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.IBaseDictionaryModule;

/**
 * Dictionary search module
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public class DictionarySearchModule<VOType extends IBaseVO> extends BaseDictionarySearchModule implements IBaseDictionaryModule
{
	private IDictionaryModel dictionaryModel;

	private DictionarySearch<VOType> dictionarySearch;

	public DictionarySearchModule(String dictionaryName, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(new ModuleVO(), moduleCallback, parameters);

		init(dictionaryName);

	}

	public DictionarySearchModule(ModuleVO moduleVO, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{

		super(moduleVO, moduleCallback, parameters);

		init(getSearchDictionaryName());
	}

	private void init(String dictionaryName)
	{
		DictionarySearchModule.this.dictionaryModel = DictionaryModelProvider.getDictionary(dictionaryName);
		DictionarySearchModule.this.dictionarySearch = new DictionarySearch<VOType>(this.dictionaryModel);

		getModuleCallback().onSuccess(DictionarySearchModule.this);

	}

	public String getTitle()
	{
		return this.dictionarySearch.getTitle();
	}

	public DictionarySearch<VOType> getDictionarySearch()
	{
		return this.dictionarySearch;
	}

	@Override
	public <ElementType> ElementType getElement(BaseModel<ElementType> baseModel)
	{
		return DictionaryElementUtil.getElement(this.dictionarySearch.getActiveFilter(), baseModel);
	}

	@Override
	public String getModuleId()
	{
		return this.dictionaryModel.getName();
	}

	public IDictionaryModel getDictionaryModel()
	{
		return this.dictionaryModel;
	}

}
