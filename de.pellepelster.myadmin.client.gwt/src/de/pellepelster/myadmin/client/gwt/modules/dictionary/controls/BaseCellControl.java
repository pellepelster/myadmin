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

import java.util.Set;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;

<<<<<<< HEAD
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
=======
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable.ITableRow;
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.BaseCellControl.ViewData;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;

public abstract class BaseCellControl<T> extends AbstractEditableCell<T, ViewData<T>>
{
<<<<<<< HEAD
=======

>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
	static class ViewData<T>
	{
		private boolean isEditing;

		private boolean isEditingAgain;

		private T value;

<<<<<<< HEAD
		private IBaseTable.ITableRow<IBaseVO> tableRow;

		public ViewData(IBaseTable.ITableRow<IBaseVO> tableRow)
		{
			this.tableRow = tableRow;
=======
		private List<IValidationMessage> validationMessages = new ArrayList<IValidationMessage>();

		public ViewData(T value)
		{
			this.value = value;
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
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
			}
		}

<<<<<<< HEAD
		public IBaseTable.ITableRow<IBaseVO> getTableRow()
=======
		public List<IValidationMessage> getValidationMessages()
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
		{
			return tableRow;
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
<<<<<<< HEAD
=======
	}

	protected InputElement getInputElement(Element parent)
	{
		return parent.getFirstChild().<InputElement> cast();
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
	}

	@Override
	public boolean isEditing(Context context, Element parent, T value)
	{
		return getOrInitViewData(context).isEditing();
	}

	protected InputElement getInputElement(Element parent)
	{
		return parent.getFirstChild().<InputElement> cast();
	}

	protected boolean enterPressed(NativeEvent event)
	{
		int keyCode = event.getKeyCode();
		return KeyUpEvent.getType().equals(event.getType()) && keyCode == KeyCodes.KEY_ENTER;
	}

	protected String format(Context context)
	{
		return getBaseControl(context).format();
	}

	protected IBaseControl<T> getBaseControl(Context context)
	{
		return getOrInitViewData(context).getTableRow().getElement(baseControlModel);
	}

	protected ViewData<T> getOrInitViewData(Context context)
	{

		if (getViewData(context.getKey()) == null)
		{
			if (!(context.getKey() instanceof IBaseTable.ITableRow))
			{
				throw new RuntimeException("IBaseTable.ITableRow expected as context key");
			}

			IBaseTable.ITableRow<IBaseVO> tableRow = (ITableRow<IBaseVO>) context.getKey();

			ViewData<T> viewData = new ViewData<T>(tableRow);
			setViewData(context.getKey(), viewData);
		}

		return getViewData(context.getKey());
	}

<<<<<<< HEAD
	protected void clearViewData(Context context)
	{
		setViewData(context.getKey(), null);
=======
	protected IBaseTable.ITableRow<?> getTableRow(Context context)
	{
		if (context.getKey() instanceof IBaseTable.ITableRow)
		{
			return (ITableRow<?>) context.getKey();
		}

		throw new RuntimeException("ITableRow expexted as context key");
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
	}

	protected BaseDictionaryControl<IBaseControlModel, T> getBaseControl(Context context)
	{
		return getTableRow(context).getElement(baseControlModel);
	}
}
