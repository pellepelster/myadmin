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
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseDictionaryModuleUI;
import de.pellepelster.myadmin.client.gwt.widgets.ImageButton;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.ISearchUpdateListener;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class DictionarySearchQueryModuleUI<VOType extends IBaseVO> extends BaseDictionaryModuleUI<DictionarySearchModule<VOType>> implements
		ISearchUpdateListener
{

	public static final String DICTIONARY_SEARCH_INPUT_PANEL_STYLE = "dictionarySearchInputPanel";

	public static final String MODULE_LOCATOR = DictionarySearchModule.MODULE_NAME_PARAMETER_NAME + "=" + DictionarySearchModule.MODULE_ID + "&"
			+ DictionarySearchModule.SHOW_QUERY_SEARCH_PARAMETER_ID + "=true";

	private final VerticalPanel verticalPanel;

	/**
	 * @param module
	 */
	public DictionarySearchQueryModuleUI(final DictionarySearchModule<VOType> module)
	{
		super(module);

		verticalPanel = new VerticalPanel();
		verticalPanel.addStyleName(DICTIONARY_SEARCH_INPUT_PANEL_STYLE);

		HorizontalPanel searchTextPanel = new HorizontalPanel();
		verticalPanel.add(searchTextPanel);

		final TextBox searchTextBox = new TextBox();
		searchTextBox.setWidth("95%");
		searchTextPanel.add(searchTextBox);

		ImageButton searchButton = new ImageButton(MyAdmin.RESOURCES.searchSearch());
		searchButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				MyAdmin.getInstance().startModule(DictionarySearchResultModuleUI.getModuleLocator(searchTextBox.getText()), Direction.CENTER.toString());
			}
		});
		searchTextPanel.add(searchButton);

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