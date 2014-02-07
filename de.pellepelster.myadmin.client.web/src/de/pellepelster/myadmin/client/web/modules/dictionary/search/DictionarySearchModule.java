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

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.module.ModuleUtils;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelProvider;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
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

	public final static String MODULE_LOCATOR = ModuleUtils.getBaseModuleUrl(MODULE_ID);

	public static final String SEARCH_UI_MODULE_ID = MODULE_ID;

	public static final String SEARCH_QUERY_UI_MODULE_ID = MODULE_ID + "Query";

	public static final String SEARCH_QUERY_UI_MODULE_LOCATOR = ModuleUtils.getBaseUIModuleUrl(SEARCH_QUERY_UI_MODULE_ID);

	public static final String SEARCH_QUERY_RESULT_UI_MODULE_ID = SEARCH_QUERY_UI_MODULE_ID + "Result";

	public static final String geSearchModuleLocator(String dictionaryName)
	{
		return ModuleUtils.getBaseUIModuleUrl(SEARCH_UI_MODULE_ID) + "&" + SEARCHDICTIONARYNAME_PARAMETER_ID + "=" + dictionaryName;
	}

	public static final String getSearchQueryResultModuleLocator(String searchText)
	{
		return ModuleUtils.getBaseUIModuleUrl(SEARCH_QUERY_RESULT_UI_MODULE_ID) + "&" + SEARCHTEXT_PARAMETER_ID + "=" + searchText;
	}

	private Optional<IDictionaryModel> dictionaryModel = Optional.absent();

	private Optional<DictionarySearch<VOType>> dictionarySearch = Optional.absent();

	private String title = MyAdmin.MESSAGES.dictionarySearch();

	public DictionarySearchModule(String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{

		super(moduleUrl, moduleCallback, parameters);

		if (ModuleUtils.hasUrlParameter(moduleUrl, SEARCHDICTIONARYNAME_PARAMETER_ID))
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
	public boolean isInstanceOf(String moduleUrl)
	{
		if (!MODULE_ID.equals(ModuleUtils.getModuleId(moduleUrl)))
		{
			return false;
		}

		if (hasParameter(SEARCHDICTIONARYNAME_PARAMETER_ID) || ModuleUtils.hasUrlParameter(moduleUrl, SEARCHDICTIONARYNAME_PARAMETER_ID))
		{
			return Objects.equal(getSearchDictionaryName(), ModuleUtils.getUrlParameter(moduleUrl, SEARCHDICTIONARYNAME_PARAMETER_ID));
		}

		return true;
	}

	@Override
	public <ElementType> ElementType getElement(BaseModel<ElementType> baseModel)
	{
		return DictionaryElementUtil.getElement(this.dictionarySearch.get().getActiveFilter(), baseModel);
	}

	public IDictionaryModel getDictionaryModel()
	{
		return this.dictionaryModel.get();
	}

}
