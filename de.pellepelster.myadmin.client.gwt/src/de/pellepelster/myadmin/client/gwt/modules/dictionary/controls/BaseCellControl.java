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
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.BaseCellControl.ViewData;

public abstract class BaseCellControl<T> extends AbstractEditableCell<T, ViewData<T>>
{
	public interface IValueHandler<T>
	{
		String format(T value);

		T parse(String value);
	}

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

		public T getOriginal()
		{
			return original;
		}

		public T getValue()
		{
			return value;
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

		public void setValue(T value)
		{
			this.value = value;
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

	private final IValueHandler<T> valueHandler;

	public BaseCellControl(IValueHandler<T> valueHandler, Set<String> consumedEvents)
	{
		super(consumedEvents);

		this.valueHandler = valueHandler;
	}

	public BaseCellControl(IValueHandler<T> valueHandler, String... consumedEvents)
	{
		super(consumedEvents);

		this.valueHandler = valueHandler;
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

	public IValueHandler<T> getValueHandler()
	{
		return valueHandler;
	}

}
