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
package de.pellepelster.myadmin.client.gwt.modules.dictionary.controls;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.gwt.ControlHelper;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IGwtControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.ReferenceControl;

public class ReferenceTextControl<VOType extends IBaseVO> extends SuggestBox implements IGwtControl
{

	private VOType vo;
	private final ReferenceControl<VOType> referenceControl;
	private final ControlHelper gwtControlHelper;

	public ReferenceTextControl(final ReferenceControl<VOType> referenceControl)
	{
		super(new VOSuggestOracle(referenceControl.getModel()));

		ensureDebugId(DictionaryModelUtil.getDebugId(referenceControl.getModel()));
		setLimit(5);
		this.referenceControl = referenceControl;
		gwtControlHelper = new ControlHelper(this, referenceControl, this, false);

		addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>()
		{
			@Override
			public void onSelection(SelectionEvent<Suggestion> selectionEvent)
			{
				if (selectionEvent.getSelectedItem() instanceof VOSuggestion)
				{
					VOSuggestion<VOType> voSuggestion = (VOSuggestion<VOType>) selectionEvent.getSelectedItem();
					vo = voSuggestion.getValue();
					referenceControl.setValue(vo);
				}

			}
		});

	}

	@Override
	public void setContent(Object content)
	{
		if (content != null)
		{
			if (content instanceof IBaseVO)
			{
				vo = (VOType) content;
				setText(DictionaryUtil.getLabel(referenceControl.getModel(), vo));
			}
			else
			{
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}
		}
		else
		{
			vo = null;
			setText("");
		}
	}

}
