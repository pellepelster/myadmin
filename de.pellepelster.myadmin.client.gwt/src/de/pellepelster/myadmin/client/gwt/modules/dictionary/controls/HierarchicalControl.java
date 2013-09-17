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

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.databinding.IValueChangeListener;
import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import de.pellepelster.myadmin.client.gwt.ControlHelper;
import de.pellepelster.myadmin.client.gwt.ui.RollOverActionImage;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IControl;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

public class HierarchicalControl extends Composite implements IControl<Widget>
{
	private final IHierarchicalControlModel hierachicalControlModel;

	private final ControlHelper gwtControlHelper;

	private IHierarchicalVO hierarchicalVO;

	private Anchor anchor;

	public HierarchicalControl(IHierarchicalControlModel hierachicalControlModel)
	{
		FlowPanel panel = new FlowPanel();

		initWidget(panel);

		// anchor
		anchor = new Anchor();
		panel.add(anchor);

		// actio image
		RollOverActionImage rollOverActionImage = new RollOverActionImage(MyAdmin.getInstance().RESOURCES.more(), new SimpleCallback<Void>()
		{

			@Override
			public void onCallback(Void t)
			{
			}
		});
		panel.add(rollOverActionImage);

		this.hierachicalControlModel = hierachicalControlModel;
		gwtControlHelper = new ControlHelper(anchor, hierachicalControlModel, true, String.class);

		ensureDebugId(DictionaryModelUtil.getDebugId(hierachicalControlModel));
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
		return hierachicalControlModel;
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

			if (content instanceof IHierarchicalVO)
			{
				hierarchicalVO = (IHierarchicalVO) content;

				String defaultLabel = "<none>";
				if (hierarchicalVO != null)
				{
					defaultLabel = hierarchicalVO.toString();
				}

				anchor.setText(DictionaryUtil.getLabel(hierachicalControlModel, hierarchicalVO, defaultLabel));
			}
			else
			{
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}
		}
		else
		{
			hierarchicalVO = null;
			anchor.setText(MyAdmin.MESSAGES.hierarchicalNone());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setValidationMessages(List<IValidationMessage> validationMessages)
	{
		gwtControlHelper.setValidationMessages(validationMessages, hierachicalControlModel);
	}

}
