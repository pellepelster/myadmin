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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.databinding.TypeHelper;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IDateControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IUIControl;

/**
 * control factory for date controls
 * 
 * @author pelle
 * 
 */
public class DateControlFactory extends BaseControlFactory<IDateControlModel>
{

	/** {@inheritDoc} */
	@Override
	public IUIControl<Widget> createControl(IDateControlModel controlModel, LAYOUT_TYPE layoutType)
	{
		return new DateControl(controlModel);
	}

	/** {@inheritDoc} */
	@Override
	public String format(IDateControlModel controlModel, Object value)
	{

		if (value != null && value instanceof Date)
		{
			DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
			return dateFormat.format((Date) value);
		}
		else
		{
			return super.format(controlModel, value);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(IBaseControlModel baseControlModel)
	{
		return baseControlModel instanceof IDateControlModel;
	}

	@Override
	public Column createColumn(final IDateControlModel controlModel, boolean editable, ListDataProvider<?> listDataProvider,
			AbstractCellTable<?> abstractCellTable)
	{

		if (editable)
		{
			final DatePickerCell datePickerCell = new DatePickerCell();

			Column<IBaseVO, Date> column = new Column<IBaseVO, Date>(datePickerCell)
			{

				@Override
				public Date getValue(IBaseVO vo)
				{
					return (Date) vo.get(controlModel.getAttributePath());
				}
			};

			FieldUpdater<IBaseVO, Date> fieldUpdater = new FieldUpdater<IBaseVO, Date>()
			{
				@Override
				public void update(int index, IBaseVO vo, Date value)
				{
					vo.set(controlModel.getAttributePath(),
							TypeHelper.convert(vo.getAttributeDescriptor(controlModel.getAttributePath()).getAttributeType(), value));
				}
			};
			column.setFieldUpdater(fieldUpdater);

			return column;
		}
		else
		{
			return super.createColumn(controlModel, editable, listDataProvider, abstractCellTable);
		}
	}

}
