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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.pellepelster.myadmin.client.base.databinding.IUIObservableValue;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.gwt.ColumnLayoutStrategy;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.IDictionaryEditorUI;
import de.pellepelster.myadmin.client.web.modules.dictionary.filter.IDictionaryFilterUI;

/**
 * Generic dictionary model based implementation of {@link IDictionaryFilterUI}
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 * @param <VOType>
 */
public class DictionaryEditor<VOType extends IBaseVO> implements IDictionaryEditorUI<VOType, Panel> {

	private final VerticalPanel verticalPanel = new VerticalPanel();

	private final IDictionaryModel dictionaryModel;

	private final List<IUIObservableValue> uiObservableValues = new ArrayList<IUIObservableValue>();

	private final ColumnLayoutStrategy layoutStrategy;

	/**
	 * Constructor for {@link DictionaryEditor}
	 * 
	 * @param dictionaryEditorModule
	 */
	public DictionaryEditor(DictionaryEditorModule<VOType> dictionaryEditorModule) {

		this.dictionaryModel = dictionaryEditorModule.getDictionaryModel();
		layoutStrategy = new ColumnLayoutStrategy(uiObservableValues, LAYOUT_TYPE.EDITOR);

		verticalPanel.setWidth("100%");
		layoutStrategy.createLayout(verticalPanel, dictionaryModel.getEditorModel().getCompositeModel());
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return verticalPanel;
	}

	/** {@inheritDoc} */
	@Override
	public List<IUIObservableValue> getUIObservableValues() {
		return uiObservableValues;
	}

}
