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
package de.pellepelster.myadmin.client.gwt.modules.dictionary;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.gwt.ColumnLayoutStrategy;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModule;

public class DictionaryEditor<VOType extends IBaseVO>
{

	private final VerticalPanel verticalPanel = new VerticalPanel();

	private final IDictionaryModel dictionaryModel;

	private final ColumnLayoutStrategy layoutStrategy = new ColumnLayoutStrategy(LAYOUT_TYPE.EDITOR);

	/**
	 * Constructor for {@link DictionaryEditor}
	 * 
	 * @param dictionaryEditorModule
	 */
	public DictionaryEditor(DictionaryEditorModule<VOType> dictionaryEditorModule)
	{

		this.dictionaryModel = dictionaryEditorModule.getDictionaryModel();

		verticalPanel.setWidth("100%");
		layoutStrategy.createLayout(verticalPanel, dictionaryEditorModule.getDictionaryEditor().getRootComposite());
	}

	public Widget getContainer()
	{
		return verticalPanel;
	}

}
