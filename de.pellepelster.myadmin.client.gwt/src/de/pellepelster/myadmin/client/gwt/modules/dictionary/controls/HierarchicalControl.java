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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.databinding.IValueChangeListener;
import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import de.pellepelster.myadmin.client.gwt.ControlHelper;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.HierarchicalVOSelectionPopup;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IControl;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

public class HierarchicalControl extends Anchor implements IControl<Widget>
{
	private final IHierarchicalControlModel hierarchicalControlModel;

	private final ControlHelper controlHelper;

	private IHierarchicalVO hierarchicalVO;

	public HierarchicalControl(final IHierarchicalControlModel hierarchicalControlModel)
	{
		this.hierarchicalControlModel = hierarchicalControlModel;
		controlHelper = new ControlHelper(this, hierarchicalControlModel, true, String.class);

		addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				HierarchicalVOSelectionPopup.create(hierarchicalControlModel, new AsyncCallback<HierarchicalVOSelectionPopup>()
				{
					@Override
					public void onSuccess(HierarchicalVOSelectionPopup result)
					{

						result.setVoSelectHandler(new SimpleCallback<IHierarchicalVO>()
						{
							@Override
							public void onCallback(IHierarchicalVO vo)
							{
								setContent(vo);
								controlHelper.fireValueChangeListeners(vo);
							}
						});
						result.show();
					}

					@Override
					public void onFailure(Throwable caught)
					{
						throw new RuntimeException(caught);
					}
				});
			}
		});

		ensureDebugId(DictionaryModelUtil.getDebugId(hierarchicalControlModel));
	}

	/** {@inheritDoc} */
	@Override
	public void addValueChangeListener(IValueChangeListener valueChangeListener)
	{
		controlHelper.getValueChangeListeners().add(valueChangeListener);
	}

	/** {@inheritDoc} */
	@Override
	public Object getContent()
	{
		return hierarchicalVO;
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getContentType()
	{
		return String.class;
	}

	/** {@inheritDoc} */
	@Override
	public IBaseControlModel getModel()
	{
		return hierarchicalControlModel;
	}

	/** {@inheritDoc} */
	@Override
	public List<IValueChangeListener> getValueChangeListeners()
	{
		return controlHelper.getValueChangeListeners();
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
		controlHelper.getValueChangeListeners().remove(valueChangeListener);
	}

	/** {@inheritDoc} */
	@Override
	public void setContent(Object content)
	{
		if (content != null)
		{

			if (content instanceof IHierarchicalVO)
			{
				hierarchicalVO = (IHierarchicalVO) content;

				String defaultLabel = "<none>";
				if (hierarchicalVO != null)
				{
					defaultLabel = hierarchicalVO.toString();
				}

				setText(DictionaryUtil.getLabel(hierarchicalControlModel, hierarchicalVO, defaultLabel));
			}
			else
			{
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}
		}
		else
		{
			hierarchicalVO = null;
			setText(MyAdmin.MESSAGES.hierarchicalNone());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setValidationMessages(List<IValidationMessage> validationMessages)
	{
		controlHelper.setValidationMessages(validationMessages, hierarchicalControlModel);
	}

}
