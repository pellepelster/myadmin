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
import de.pellepelster.myadmin.client.gwt.GwtStyles;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.ActionBar;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseDictionaryModuleUI;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.DictionaryFilterPanel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.DictionaryResultPanel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.ISearchUpdateListener;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class DictionarySearchModuleUI<VOType extends IBaseVO> extends BaseDictionaryModuleUI<DictionarySearchModule<VOType>> implements ISearchUpdateListener
{
	public static final String MODULE_ID = "DictionarySearchModuleUI";

	private static final String DICTIONARY_CREATE_BUTTON_DEBUG_ID = "DictionaryCreateButton";

	private static final String DICTIONARY_SEARCH_BUTTON_DEBUG_ID = "DictionarySearchButton";

	private final VerticalPanel verticalPanel;

	private final HTML searchTitle;

	/**
	 * @param module
	 */
	public DictionarySearchModuleUI(final DictionarySearchModule<VOType> module)
	{
		super(module, MODULE_ID);

		verticalPanel = new VerticalPanel();
		verticalPanel.addStyleName(GwtStyles.DEBUG_BORDER);
		verticalPanel.ensureDebugId(DictionarySearchModule.MODULE_ID + "-" + module.getDictionaryModel().getName());
		verticalPanel.setWidth("100%");

		// - action panel ------------------------------------------------------
		ActionBar actionBar = new ActionBar();
		verticalPanel.add(actionBar);

		// - title -------------------------------------------------------------
		searchTitle = new HTML(module.getTitle());
		searchTitle.addStyleName(GwtStyles.TITLE);
		verticalPanel.add(searchTitle);

		// searchModel.getResultModel()
		final DictionaryResultPanel<VOType> dictionaryResultPanel = new DictionaryResultPanel<VOType>(getModule().getDictionaryModel(), getModule()
				.getDictionarySearch().getDictionaryResult());

		if (getModule().getDictionarySearch().hasFilter())
		{
			// searchModel.getFilterModel().get(0)
			final DictionaryFilterPanel<VOType> dictionaryFilter = new DictionaryFilterPanel<VOType>(getModule().getDictionarySearch().getActiveFilter(),
					module.getDictionaryModel().getVoName());

			// dictionaryFilter.addStyleName(GwtStyles.VERTICAL_SPACING);
			verticalPanel.add(dictionaryFilter);

			actionBar.addToButtonGroup(module.getModuleUrl(), MyAdmin.RESOURCES.searchSearch(), MyAdmin.MESSAGES.searchSearch(), new ClickHandler()
			{
				/** {@inheritDoc} */
				@Override
				public void onClick(ClickEvent event)
				{
					module.getDictionarySearch().search();
				}
			}, DictionarySearchModule.MODULE_ID + "-" + module.getDictionaryModel().getName() + "-" + DICTIONARY_SEARCH_BUTTON_DEBUG_ID);
		}
		else
		{
			actionBar.addToButtonGroup(module.getModuleUrl(), MyAdmin.RESOURCES.searchSearch(), MyAdmin.MESSAGES.searchSearch(), new ClickHandler()
			{
				/** {@inheritDoc} */
				@Override
				public void onClick(ClickEvent event)
				{
					module.getDictionarySearch().search();
				}
			}, DictionarySearchModule.MODULE_ID + "-" + module.getDictionaryModel().getName() + "-" + DICTIONARY_SEARCH_BUTTON_DEBUG_ID);

		}

		actionBar.addToButtonGroup(module.getModuleUrl(), MyAdmin.RESOURCES.dictionaryCreate(), MyAdmin.MESSAGES.dictionaryCreate(), new ClickHandler()
		{
			/** {@inheritDoc} */
			@Override
			public void onClick(ClickEvent event)
			{
				DictionaryEditorModuleFactory.openEditor(getModule().getDictionaryModel().getName());
			}
		}, DictionarySearchModule.MODULE_ID + "-" + module.getDictionaryModel().getName() + "-" + DICTIONARY_CREATE_BUTTON_DEBUG_ID);

		// dictionaryResultPanel.addStyleName(GwtStyles.VERTICAL_SPACING);
		dictionaryResultPanel.setWidth("100%");
		verticalPanel.add(dictionaryResultPanel);

		getModule().getDictionarySearch().addUpdateListener(this);
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
		searchTitle.setText(getModule().getTitle());
	}
}
