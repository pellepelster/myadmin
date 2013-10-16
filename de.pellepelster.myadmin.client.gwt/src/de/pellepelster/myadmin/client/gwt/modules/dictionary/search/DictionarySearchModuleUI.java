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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.ISearchModel;
import de.pellepelster.myadmin.client.gwt.GwtStyles;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.ActionBar;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseDictionaryModuleUI;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.DictionaryResult;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.GwtDictionaryFilter;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModule;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class DictionarySearchModuleUI<VOType extends IBaseVO> extends BaseDictionaryModuleUI<DictionarySearchModule>
{

	private static final String DICTIONARY_CREATE_BUTTON_DEBUG_ID = "DictionaryCreateButton";

	private static final String DICTIONARY_SEARCH_BUTTON_DEBUG_ID = "DictionarySearchButton";

	private final VerticalPanel verticalPanel;

	/**
	 * @param module
	 */
	public DictionarySearchModuleUI(final DictionarySearchModule module)
	{
		super(module);

		verticalPanel = new VerticalPanel();
		verticalPanel.addStyleName(GwtStyles.DEBUG_BORDER);
		verticalPanel.ensureDebugId(DictionarySearchModule.MODULE_ID + "-" + module.getDictionaryModel().getName());
		verticalPanel.setWidth("100%");

		// - action panel ------------------------------------------------------
		ActionBar actionBar = new ActionBar();
		verticalPanel.add(actionBar);

		// - title -------------------------------------------------------------
		final HTML searchTitle = new HTML(module.getTitle());
		searchTitle.addStyleName(GwtStyles.SEARCH_TITLE);
		verticalPanel.add(searchTitle);

		final ISearchModel searchModel = module.getDictionaryModel().getSearchModel();

		// searchModel.getResultModel()
		final DictionaryResult<VOType> dictionaryResult = new DictionaryResult<VOType>(getModule().getDictionaryModel().getName(), null);
		dictionaryResult.setResultsChangedCallback(new SimpleCallback<Integer>()
		{

			@Override
			public void onCallback(Integer resultCount)
			{
				searchTitle.setText(DictionaryUtil.getSearchTitle(module.getDictionaryModel(), resultCount));
			}
		});

		if (!searchModel.getFilterModel().isEmpty())
		{
			// searchModel.getFilterModel().get(0)
			final GwtDictionaryFilter<VOType> dictionaryFilter = new GwtDictionaryFilter<VOType>(null);

			Panel filterPanel = dictionaryFilter.getContainer();
			filterPanel.addStyleName(GwtStyles.VERTICAL_SPACING);
			verticalPanel.add(filterPanel);

			actionBar.addButtonBarStart(MyAdmin.RESOURCES.searchSearch(), MyAdmin.MESSAGES.searchSearch(), new ClickHandler()
			{
				/** {@inheritDoc} */
				@Override
				public void onClick(ClickEvent event)
				{
					dictionaryResult.setFilter(dictionaryFilter.getFilter());
				}
			}, DictionarySearchModule.MODULE_ID + "-" + module.getDictionaryModel().getName() + "-" + DICTIONARY_SEARCH_BUTTON_DEBUG_ID);
		}
		else
		{
			actionBar.addButtonBarStart(MyAdmin.RESOURCES.searchSearch(), MyAdmin.MESSAGES.searchSearch(), new ClickHandler()
			{
				/** {@inheritDoc} */
				@Override
				public void onClick(ClickEvent event)
				{
					dictionaryResult.setFilter(dictionaryResult.getEmptyFilter(searchModel));
				}
			}, DictionarySearchModule.MODULE_ID + "-" + module.getDictionaryModel().getName() + "-" + DICTIONARY_SEARCH_BUTTON_DEBUG_ID);

		}

		actionBar.addButtonBarEnd(MyAdmin.RESOURCES.searchCreate(), MyAdmin.MESSAGES.searchCreate(), new ClickHandler()
		{

			/** {@inheritDoc} */
			@Override
			public void onClick(ClickEvent event)
			{
				DictionaryEditorModuleFactory.openEditor(getModule().getDictionaryModel().getName());
			}
		}, DictionarySearchModule.MODULE_ID + "-" + module.getDictionaryModel().getName() + "-" + DICTIONARY_CREATE_BUTTON_DEBUG_ID);

		Panel resultPanel = dictionaryResult.getContainer();
		resultPanel.addStyleName(GwtStyles.VERTICAL_SPACING);
		resultPanel.setWidth("100%");
		verticalPanel.add(resultPanel);
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
		return getModule().getDictionaryModel().getTitle();
	}
}
