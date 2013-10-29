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

import java.util.Date;

import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IDateControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.DateControl;

/**
 * control factory for date controls
 * 
 * @author pelle
 * 
 */
public class DateControlFactory extends BaseControlFactory<IDateControlModel, DateControl>
{

	/** {@inheritDoc} */
	@Override
	public Widget createControl(DateControl dateControl, LAYOUT_TYPE layoutType)
	{
		return new GwtDateControl(dateControl);
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl baseControl)
	{
		return baseControl instanceof DateControl;
	}

	@Override
	public Column<IBaseTable.ITableRow<IBaseVO>, ?> createColumn(final DateControl dateControl, boolean editable, ListDataProvider<?> listDataProvider,
			AbstractCellTable<?> abstractCellTable)
	{
		if (editable)
		{
			final DatePickerCell datePickerCell = new DatePickerCell();

			Column<IBaseTable.ITableRow<IBaseVO>, Date> column = new Column<IBaseTable.ITableRow<IBaseVO>, Date>(datePickerCell)
			{

				@Override
				public Date getValue(IBaseTable.ITableRow<IBaseVO> tableRow)
				{
					Object date = tableRow.getElement(dateControl.getModel()).getValue();

					if (date == null)
					{
						return new Date();
					}
					else
					{
						return (Date) tableRow.getElement(dateControl.getModel()).getValue();
					}
				}
			};

			FieldUpdater<IBaseTable.ITableRow<IBaseVO>, Date> fieldUpdater = new FieldUpdater<IBaseTable.ITableRow<IBaseVO>, Date>()
			{
				@Override
				public void update(int index, IBaseTable.ITableRow<IBaseVO> tableRow, Date value)
				{
					tableRow.getElement(dateControl.getModel()).setValue(value);
				}
			};
			column.setFieldUpdater(fieldUpdater);

			return column;
		}
		else
		{
			return super.createColumn(dateControl, editable, listDataProvider, abstractCellTable);
		}
	}
}
