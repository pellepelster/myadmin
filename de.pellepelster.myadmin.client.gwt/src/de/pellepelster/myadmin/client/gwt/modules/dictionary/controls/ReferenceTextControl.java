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
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.ReferenceControl;

public class ReferenceTextControl extends SuggestBox
{

	private IBaseVO vo;
	private final ReferenceControl referenceControl;
	private final ControlHelper gwtControlHelper;

	public ReferenceTextControl(final ReferenceControl referenceControl)
	{
		super(new VOSuggestOracle(referenceControl.getModel()));

		ensureDebugId(DictionaryModelUtil.getDebugId(referenceControl.getModel()));
		setLimit(5);
		this.referenceControl = referenceControl;
		gwtControlHelper = new ControlHelper(this, referenceControl, false, IBaseVO.class);

		addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>()
		{
			@Override
			public void onSelection(SelectionEvent<Suggestion> selectionEvent)
			{
				if (selectionEvent.getSelectedItem() instanceof VOSuggestion)
				{
					VOSuggestion voSuggestion = (VOSuggestion) selectionEvent.getSelectedItem();
					vo = voSuggestion.getVo();
					gwtControlHelper.fireValueChangeListeners(vo);
				}

			}
		});

	}

	public void setContent(Object content)
	{
		if (content != null)
		{
			if (content instanceof IBaseVO)
			{
				vo = (IBaseVO) content;
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
