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

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelProvider;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.SearchResultItemVO;
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
	public static final String SHOW_QUERY_SEARCH_PARAMETER_ID = "showQuerySearch";

	public static final String QUERY_TEXT_PARAMETER_ID = "queryText";

	private Optional<IDictionaryModel> dictionaryModel = Optional.absent();

	private Optional<DictionarySearch<VOType>> dictionarySearch = Optional.absent();

	private String title = MyAdmin.MESSAGES.dictionarySearch();

	public DictionarySearchModule(String dictionaryName, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(new ModuleVO(), moduleCallback, parameters);

		init(dictionaryName);

	}

	public DictionarySearchModule(ModuleVO moduleVO, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{

		super(moduleVO, moduleCallback, parameters);

		if (hasParameter(this.SEARCHDICTIONARYNAME_PARAMETER_ID))
		{
			init(getSearchDictionaryName());
		}
		else
		{
			moduleCallback.onSuccess(this);
		}
	}

	private void init(String dictionaryName)
	{
		DictionarySearchModule.this.dictionaryModel = Optional.of(DictionaryModelProvider.getDictionary(dictionaryName));
		DictionarySearchModule.this.dictionarySearch = Optional.of(new DictionarySearch<VOType>(this.dictionaryModel.get()));

		getModuleCallback().onSuccess(DictionarySearchModule.this);

	}

	@Override
	public String getTitle()
	{
		if (this.dictionarySearch.isPresent())
		{
			return this.dictionarySearch.get().getTitle();
		}
		else
		{
			return this.title;
		}
	}

	public void search(String query, AsyncCallback<List<SearchResultItemVO>> callback)
	{
		this.title = MyAdmin.MESSAGES.dictionarySearchResults(query);
		MyAdmin.getInstance().getRemoteServiceLocator().getDictionarySearchService().search(query, callback);
	}

	public DictionarySearch<VOType> getDictionarySearch()
	{
		return this.dictionarySearch.get();
	}

	@Override
	public <ElementType> ElementType getElement(BaseModel<ElementType> baseModel)
	{
		return DictionaryElementUtil.getElement(this.dictionarySearch.get().getActiveFilter(), baseModel);
	}

	@Override
	public String getModuleId()
	{
		if (this.dictionaryModel.isPresent())
		{
			return this.dictionaryModel.get().getName();
		}
		else
		{
			return super.getModuleId();
		}
	}

	public IDictionaryModel getDictionaryModel()
	{
		return this.dictionaryModel.get();
	}

}
