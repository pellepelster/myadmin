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

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.ReferenceControl;

/**
 * control factory for reference controls
 * 
 * @author pelle
 * 
 */
public class ReferenceControlFactory<VOType extends IBaseVO> extends BaseControlFactory<IReferenceControlModel, ReferenceControl<VOType>>
{

	/** {@inheritDoc} */
	@Override
	public Widget createControl(ReferenceControl<VOType> referenceControl, LAYOUT_TYPE layoutType)
	{
		switch (referenceControl.getModel().getControlType())
		{
			case DROPDOWN:
				return new ReferenceDropdownControl(referenceControl);
			default:
				return new ReferenceTextControl(referenceControl);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl<?, ?> baseControl)
	{
		return baseControl instanceof ReferenceControl;
	}

	/** {@inheritDoc} */
	@Override
	public Column<IBaseTable.ITableRow<IBaseVO>, ?> createColumn(final ReferenceControl<VOType> referenceControl, boolean editable,
			final ListDataProvider<?> listDataProvider, final AbstractCellTable<?> abstractCellTable)
	{

		Column<IBaseTable.ITableRow<IBaseVO>, VOType> column;

		if (editable)
		{
			final BaseCellControl<VOType> editTextCell;

			switch (referenceControl.getModel().getControlType())
			{
				default:
					editTextCell = new SuggestCellControl<VOType>(referenceControl.getModel(), new VOSuggestOracle(referenceControl.getModel()));
					break;
			}

			column = new Column<IBaseTable.ITableRow<IBaseVO>, VOType>(editTextCell)
			{

				@Override
				public VOType getValue(IBaseTable.ITableRow<IBaseVO> tableRow)
				{
					return (VOType) tableRow.getElement(referenceControl.getModel()).getValue();
				}
			};

			FieldUpdater<IBaseTable.ITableRow<IBaseVO>, VOType> fieldUpdater = new FieldUpdater<IBaseTable.ITableRow<IBaseVO>, VOType>()
			{
				@Override
				public void update(int index, IBaseTable.ITableRow<IBaseVO> tableRow, VOType value)
				{
					tableRow.getElement(referenceControl.getModel()).setValue(value);
				}
			};
			column.setFieldUpdater(fieldUpdater);

		}
		else
		{
			column = new Column<IBaseTable.ITableRow<IBaseVO>, VOType>(new ReferenceCell<VOType>(referenceControl.getModel()))
			{
				@Override
				public VOType getValue(IBaseTable.ITableRow<IBaseVO> tableRow)
				{
					return (VOType) tableRow.getElement(referenceControl.getModel()).getValue();
				}
			};
		}

		return column;

	}
}
