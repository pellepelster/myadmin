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
package de.pellepelster.myadmin.client.gwt.modules.dictionary.search;

import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.gwt.GwtStyles;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseDictionaryModuleUI;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseGwtModuleUI;
import de.pellepelster.myadmin.client.web.entities.dictionary.SearchResultItemVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.ISearchUpdateListener;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class DictionarySearchResultModuleUI<VOType extends IBaseVO> extends BaseDictionaryModuleUI<DictionarySearchModule<VOType>> implements
		ISearchUpdateListener
{
	public static final String MODULE_ID = DictionarySearchResultModuleUI.class.getName();

	private static final String DICTIONARY_SEARCH_RESULT_PANEL_STYLE = "dictionarySearchResultPanel";

	private static final String DICTIONARY_SEARCH_RESULT_ITEM_PANEL_STYLE = "dictionarySearchResultItemPanel";

	private static final String DICTIONARY_SEARCH_RESULT_ITEM_TITLE_STYLE = "dictionarySearchResultItemTitle";

	public static final String MODULE_URL = BaseGwtModuleUI.getModuleUrl(DictionarySearchModule.MODULE_ID, MODULE_ID);

	private final VerticalPanel verticalPanel;

	/**
	 * @param module
	 */
	public DictionarySearchResultModuleUI(final DictionarySearchModule<VOType> module)
	{
		super(module, MODULE_ID);

		String queryString = module.getSearchText();

		verticalPanel = new VerticalPanel();

		HTML title = new HTML(module.getTitle());
		title.addStyleName(GwtStyles.TITLE);
		verticalPanel.add(title);
		verticalPanel.setWidth("100%");

		final VerticalPanel resultPanel = new VerticalPanel();
		verticalPanel.add(resultPanel);
		resultPanel.addStyleName(DICTIONARY_SEARCH_RESULT_PANEL_STYLE);
		resultPanel.setWidth("100%");

		getModule().search(queryString, new BaseErrorAsyncCallback<List<SearchResultItemVO>>()
		{

			@Override
			public void onSuccess(List<SearchResultItemVO> result)
			{
				for (int i = 0; i < resultPanel.getWidgetCount(); i++)
				{
					resultPanel.remove(i);
				}

				for (SearchResultItemVO searchResultItem : result)
				{
					VerticalPanel resultItemPanel = new VerticalPanel();
					resultItemPanel.setWidth("100%");
					resultItemPanel.addStyleName(DICTIONARY_SEARCH_RESULT_ITEM_PANEL_STYLE);
					HTML title = new HTML(searchResultItem.getTitle());
					title.addStyleName(DICTIONARY_SEARCH_RESULT_ITEM_TITLE_STYLE);
					resultItemPanel.add(title);
					resultPanel.add(resultItemPanel);
				}

			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer()
	{
		return verticalPanel;
	}

	@Override
	public String getTitle()
	{
		return getModule().getTitle();
	}

	@Override
	public void onUpdate()
	{
	}
}
