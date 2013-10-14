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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.databinding.IValueChangeListener;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IEnumarationControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.gwt.ControlHelper;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IUIControl;

public class ReferenceDropdownControl extends ListBox implements IUIControl<Widget>
{

	private final IReferenceControlModel referenceControlModel;
	private final ControlHelper gwtControlHelper;

	public ReferenceDropdownControl(final IReferenceControlModel referenceControlModel)
	{
		super(false);

		this.referenceControlModel = referenceControlModel;

		ensureDebugId(DictionaryModelUtil.getDebugId(referenceControlModel));
		gwtControlHelper = new ControlHelper(this, referenceControlModel, false, String.class);

		addChangeHandler(new ChangeHandler()
		{

			@Override
			public void onChange(ChangeEvent event)
			{
				gwtControlHelper.fireValueChangeListeners(null);
			}
		});

		ControlUtil.populateListBox(referenceControlModel, this);

	}

	public static List<String> getSortedEnumList(IEnumarationControlModel enumarationControlModel)
	{
		List<String> enumList = new ArrayList<String>();
		enumList.addAll(enumarationControlModel.getEnumeration().values());
		Collections.sort(enumList);

		return enumList;
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
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getContentType()
	{
		return String.class;
	}

	public static String getEnumForText(IEnumarationControlModel enumarationControlModel, String text)
	{
		if (text == null || text.isEmpty())
		{
			return null;
		}

		for (Map.Entry<String, String> enumEntry : enumarationControlModel.getEnumeration().entrySet())
		{
			if (enumEntry.getValue().equals(text))
			{
				return enumEntry.getKey();
			}
		}

		throw new RuntimeException("no enum found for text '" + text + "'");
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

			if (content instanceof String || content instanceof Enum<?>)
			{

				for (int i = 0; i < getItemCount(); i++)
				{
					if (getItemText(i).equals(content.toString()))
					{
						setSelectedIndex(i);
					}
				}

			}
			else
			{
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}
		}
		else
		{
			super.setSelectedIndex(0);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setValidationMessages(List<IValidationMessage> validationMessages)
	{
		gwtControlHelper.setValidationMessages(validationMessages, referenceControlModel);
	}

}
