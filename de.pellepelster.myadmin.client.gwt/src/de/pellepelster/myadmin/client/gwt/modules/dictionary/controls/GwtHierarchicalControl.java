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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;

import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.util.SimpleCallback;
import de.pellepelster.myadmin.client.gwt.ControlHelper;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.HierarchicalVOSelectionPopup;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.HierarchicalControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IGwtControl;

public class GwtHierarchicalControl extends Anchor implements IGwtControl
{
	private final HierarchicalControl hierarchicalControl;

	private IHierarchicalVO hierarchicalVO;

	public GwtHierarchicalControl(final HierarchicalControl hierarchicalControl)
	{
		this.hierarchicalControl = hierarchicalControl;
		new ControlHelper(this, hierarchicalControl, this, true);

		addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				HierarchicalVOSelectionPopup.create(hierarchicalControl.getModel(), new AsyncCallback<HierarchicalVOSelectionPopup>()
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
								hierarchicalControl.setValue(vo);
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

		ensureDebugId(DictionaryModelUtil.getDebugId(hierarchicalControl.getModel()));
	}

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

				setText(DictionaryUtil.getLabel(hierarchicalControl.getModel(), hierarchicalVO, defaultLabel));
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

}
