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
import java.util.List;
import java.util.Set;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.BaseCellControl.ViewData;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;

public abstract class BaseCellControl<T> extends AbstractEditableCell<T, ViewData<T>>
{

	static class ViewData<T>
	{
		private boolean isEditing;

		private boolean isEditingAgain;

		private T original;

		private T value;

		private List<IValidationMessage> validationMessages = new ArrayList<IValidationMessage>();

		public ViewData(T value)
		{
			this.value = value;
		}

		public boolean isEditing()
		{
			return isEditing;
		}

		public boolean isEditingAgain()
		{
			return isEditingAgain;
		}

		public void setEditing(boolean isEditing)
		{
			boolean wasEditing = this.isEditing;
			this.isEditing = isEditing;

			if (!wasEditing && isEditing)
			{
				isEditingAgain = true;
				original = value;
			}
		}

		public List<IValidationMessage> getValidationMessages()
		{
			return validationMessages;
		}

		public void setValidationMessages(List<IValidationMessage> validationMessages)
		{
			this.validationMessages = validationMessages;
		}

	}

	private final IBaseControlModel baseControlModel;

	public BaseCellControl(IBaseControlModel baseControlModel, Set<String> consumedEvents)
	{
		super(consumedEvents);

		this.baseControlModel = baseControlModel;
	}

	public BaseCellControl(IBaseControlModel baseControlModel, String... consumedEvents)
	{
		super(consumedEvents);

		this.baseControlModel = baseControlModel;
	}

	protected InputElement getInputElement(Element parent)
	{
		return parent.getFirstChild().<InputElement> cast();
	}

	@Override
	public boolean isEditing(Context context, Element parent, T value)
	{
		ViewData<T> viewData = getAndInitViewData(context);

		return viewData == null ? false : viewData.isEditing();
	}

	protected boolean enterPressed(NativeEvent event)
	{
		int keyCode = event.getKeyCode();
		return KeyUpEvent.getType().equals(event.getType()) && keyCode == KeyCodes.KEY_ENTER;
	}

	protected ViewData<T> getAndInitViewData(Context context)
	{
		return getAndInitViewData(context, null);
	}

	protected ViewData<T> getAndInitViewData(Context context, T value)
	{
		if (getViewData(context.getKey()) == null)
		{
			ViewData<T> viewData = new ViewData<T>(value);
			setViewData(context.getKey(), viewData);
		}
		return getViewData(context.getKey());
	}

	protected IBaseTable.ITableRow<?> getTableRow(Context context)
	{
		if (context.getKey() instanceof IBaseTable.ITableRow)
		{
			return (ITableRow<?>) context.getKey();
		}

		throw new RuntimeException("ITableRow expexted as context key");
	}

	protected BaseDictionaryControl<IBaseControlModel, T> getBaseControl(Context context)
	{
		return getTableRow(context).getElement(baseControlModel);
	}
}
