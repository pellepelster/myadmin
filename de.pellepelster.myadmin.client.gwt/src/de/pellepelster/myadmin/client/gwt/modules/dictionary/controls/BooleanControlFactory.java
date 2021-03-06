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

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBooleanControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BooleanControl;

/**
 * control factory for boolean controls
 * 
 * @author pelle
 * 
 */
public class BooleanControlFactory extends BaseControlFactory<IBooleanControlModel, BooleanControl>
{

	/** {@inheritDoc} */
	@Override
	public Widget createControl(BooleanControl booleanControl, LAYOUT_TYPE layoutType)
	{
		return new GwtBooleanControl(booleanControl);
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl<?, ?> baseControlModel)
	{
		return baseControlModel instanceof BooleanControl;
	}

	@Override
	public Column<IBaseTable.ITableRow<IBaseVO>, ?> createColumn(final BooleanControl booleanControl, boolean editable, ListDataProvider<?> listDataProvider,
			AbstractCellTable<?> abstractCellTable)
	{

		if (editable)
		{
			final CheckboxCell checkboxCell = new CheckboxCell();

			Column<IBaseTable.ITableRow<IBaseVO>, Boolean> column = new Column<IBaseTable.ITableRow<IBaseVO>, Boolean>(checkboxCell)
			{

				@Override
				public Boolean getValue(IBaseTable.ITableRow<IBaseVO> tableRow)
				{
					return (Boolean) tableRow.getElement(booleanControl.getModel()).getValue();
				}
			};

			FieldUpdater<IBaseTable.ITableRow<IBaseVO>, Boolean> fieldUpdater = new FieldUpdater<IBaseTable.ITableRow<IBaseVO>, Boolean>()
			{
				@SuppressWarnings("unchecked")
				@Override
				public void update(int index, IBaseTable.ITableRow<IBaseVO> tableRow, Boolean value)
				{
					tableRow.getElement(booleanControl.getModel()).setValue(value);
				}
			};
			column.setFieldUpdater(fieldUpdater);

			return column;
		}
		else
		{
			return super.createColumn(booleanControl, editable, listDataProvider, abstractCellTable);
		}
	}

}
