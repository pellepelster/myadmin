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

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseCellTable;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.BaseCellControl.IValueHandler;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.BaseCellControl.ViewData;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.IValidator;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.ValidationUtils;

/**
 * @author pelle
 * 
 */
public abstract class BaseControlFactory<ControlModelType extends IBaseControlModel, ControlType extends BaseDictionaryControl<ControlModelType, ?>> implements
		IGwtControlFactory<ControlModelType, ControlType>
{

	@Override
	public Column<IBaseTable.ITableRow<IBaseVO>, ?> createColumn(final ControlType baseControl, boolean editable, final ListDataProvider<?> listDataProvider,
			final AbstractCellTable<?> abstractCellTable)
	{

		Column<IBaseTable.ITableRow<IBaseVO>, String> column;

		if (editable)
		{

			final EditTextCellWithValidation editTextCell = new EditTextCellWithValidation(baseControl, new IValueHandler()
			{

				@Override
				public String format(Object value)
				{
					if (value != null)
					{
						return value.toString();
					}
					else
					{
						return "";
					}
				}

				@Override
				public Object parse(String value)
				{
					return value.toString();
				}
			});

			column = new TableRowColumn(baseControl.getModel(), editTextCell);

			FieldUpdater<IBaseTable.ITableRow<IBaseVO>, String> fieldUpdater = new FieldUpdater<IBaseTable.ITableRow<IBaseVO>, String>()
			{
				@SuppressWarnings("unchecked")
				@Override
				public void update(int index, IBaseTable.ITableRow<IBaseVO> tableRow, String value)
				{

					Object key = BaseCellTable.KEYPROVIDER.getKey(tableRow);

					List<IValidator> validators = createValidators(baseControl);
					List<IValidationMessage> validationMessages = ValidationUtils.validate(validators, value, baseControl.getModel());

					ViewData<String> viewData = (ViewData<String>) editTextCell.getViewData(key);

					if (validationMessages != null && ValidationUtils.hasError(validationMessages))
					{
						viewData.setValidationMessages(validationMessages);
						// dataGrid.redraw();
					}
					else
					{
						viewData.getValidationMessages().clear();

						// vo.set(baseControl.getModel().getAttributePath(),
						// TypeHelper.convert(vo.getAttributeDescriptor(baseControl.getModel().getAttributePath()).getAttributeType(),
						// value));
					}

					listDataProvider.refresh();
				}
			};
			column.setFieldUpdater(fieldUpdater);

		}
		else
		{
			column = new TableRowColumn(baseControl.getModel(), new TextCell());
		}

		return column;

	}

}
