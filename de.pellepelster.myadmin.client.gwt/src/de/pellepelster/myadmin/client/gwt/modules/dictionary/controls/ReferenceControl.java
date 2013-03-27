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

import java.util.List;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.databinding.IValueChangeListener;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.ModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.gwt.ControlHelper;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IControl;

public class ReferenceControl extends SuggestBox implements IControl<Widget>
{

	private IBaseVO vo;
	private final IReferenceControlModel referenceControlModel;
	private final ControlHelper gwtControlHelper;

	public ReferenceControl(final IReferenceControlModel referenceControlModel)
	{
		super(new VOSuggestOracle(referenceControlModel));

		ensureDebugId(ModelUtil.getDebugId(referenceControlModel));

		addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>()
		{
			@Override
			public void onSelection(SelectionEvent<Suggestion> selectionEvent)
			{
				if (selectionEvent.getSelectedItem() instanceof VOSuggestion)
				{
					VOSuggestion voSuggestion = (VOSuggestion) selectionEvent.getSelectedItem();
					vo = voSuggestion.getVo();
					gwtControlHelper.fireValueChangeListeners(referenceControlModel.getAttributePath(), vo);
				}

			}
		});

		setLimit(5);
		this.referenceControlModel = referenceControlModel;
		gwtControlHelper = new ControlHelper(this, referenceControlModel, false, IBaseVO.class);
	}

	/** {@inheritDoc} */
	@Override
	public void addValueChangeListener(IValueChangeListener valueChangeListener)
	{
		gwtControlHelper.getValueChangeListeners().add(valueChangeListener);
	}

	/** {@inheritDoc} */
	@Override
	public Object getContent()
	{
		return vo;
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getContentType()
	{
		return IBaseVO.class;
	}

	/** {@inheritDoc} */
	@Override
	public IBaseControlModel getModel()
	{
		return referenceControlModel;
	}

	/** {@inheritDoc} */
	@Override
	public List<IValueChangeListener> getValueChangeListeners()
	{
		return gwtControlHelper.getValueChangeListeners();
	}

	/** {@inheritDoc} */
	@Override
	public Widget getWidget()
	{
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public void removeValueChangeListener(IValueChangeListener valueChangeListener)
	{
		gwtControlHelper.getValueChangeListeners().remove(valueChangeListener);
	}

	/** {@inheritDoc} */
	@Override
	public void setContent(Object content)
	{
		if (content != null)
		{
			if (content instanceof IBaseVO)
			{
				vo = (IBaseVO) content;
				setText(DictionaryUtil.getLabel(referenceControlModel, vo));
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

	/** {@inheritDoc} */
	@Override
	public void setValidationMessages(List<IValidationMessage> validationMessages)
	{
		gwtControlHelper.setValidationMessages(validationMessages);
	}

}
